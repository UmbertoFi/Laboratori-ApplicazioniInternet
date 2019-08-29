import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';
import {LineaService} from './_services/linea.service';
import {AlertService, AuthenticationService, UserService} from './_services';
import {Router} from '@angular/router';
import {Bambino} from './_models/bambino';
import {nomeLinea} from './_models/nomeLinea';
import {CorsaNew} from './_models/corsaNew';
import {TrattaNew} from './_models/trattaNew';
import {Ruolo} from './_models';
import {Notifica} from './_models/notifica';
import {WebSocketService} from './_services/websocket.service';
import {presaVisione} from './_models/presaVisione';
import {saveAs} from 'file-saver';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {
  title = 'Pedibus';

  bambini: Bambino[];
  nomiLinee: nomeLinea[];
  corse: CorsaNew[];
  tratte: TrattaNew;
  data: string;


  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;
  openDateForm: boolean = false;
  ruoli: Ruolo[];
  checkAdmin: boolean = false;
  checkAccompagnatore: boolean = false;
  checkSystemAdmin: boolean = false;

  notifications: Notifica[];
  notifica: Notifica;
  x: number;
  p: presaVisione;

  constructor(private webSocketService: WebSocketService, private lineaService: LineaService, private userService: UserService, private authenticationService: AuthenticationService, private alertService: AlertService, private router: Router) {
    this.notifications = new Array<Notifica>();

    const promiseZero = fetch('http://localhost:8080/utility/primovalorenotifiche/' + localStorage.getItem('username'), {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    }).then((data) => {
      return data.json();
    }).then((data) => {
      this.x = data;
      if (this.x != 0) {
        this.notifica = new Notifica(this.x, '', '', '', '', '', 0);
        this.notifications.push(this.notifica);
      }
    });


// Open connection with server socket
    const stompClient = this.webSocketService.connect();
    stompClient.connect({}, frame => {

      // Subscribe to notification topic
      stompClient.subscribe('/user/' + localStorage.getItem('username') + '/queue/reply', notifications => {

        // Update notifications attribute with the recent messsage sent from the server
        this.notifica = JSON.parse(notifications.body);
        this.notifications.push(this.notifica);
      });
    });
  }

  ngOnInit(): void {
    if (localStorage.getItem('access_token') == null) {
      this.alertService.error('Utente non ancora loggato!', true);
      this.router.navigate(['/login']);
    }

    let promiseRuoli = fetch('http://localhost:8080/utility/ruoli', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.ruoli = data;
        return;
      }).then((data) => {
        for (let r of this.ruoli) {
          console.log(r);
          if (r.ruolo.localeCompare('admin') == 0) {
            this.checkAdmin = true;
          } else if (r.ruolo.localeCompare('system-admin') == 0) {
            this.checkSystemAdmin = true;
          } else if (r.ruolo.localeCompare('accompagnatore') == 0) {
            this.checkAccompagnatore = true;
          }
        }
      });

    let promiseBambini = fetch('http://localhost:8080/utility/children', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.bambini = data;
      });

    let promiseLinea = fetch('http://localhost:8080/lines', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.nomiLinee = data;
        return;
      }).then(() => {
        let promiseCorsa = fetch('http://localhost:8080/utility/corse/' + this.nomiLinee[0].nome, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
          }
        })
          .then((data) => {
            return data.json();
          }).then((data) => {
            this.corse = data;
            return;
          }).then(() => {
            this.length = this.corse.length;  // Poichè corse ha Andata e Ritorno
            this.corse.sort((a, b) => a.data.localeCompare(b.data));
            let j;
            let date;
            const today = new Date();
            const dd = String(today.getDate()).padStart(2, '0');
            const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
            const yyyy = today.getFullYear();
            const cur = yyyy + '/' + mm + '/' + dd;
            const curdate = new Date(cur).getTime();
            for (j = 0; j < this.corse.length; j++) {
              date = new Date(this.corse[j].data).getTime();
              if (date - curdate >= 0) {
                break;
              }
            }
            this.pageIndex = j;
          })
          .then(() => {
            let promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[0].nome + '/' + this.corse[this.pageIndex].data, {
              headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('access_token')
              }
            })
              .then((data) => {
                return data.json();
              }).then((data) => {
                this.tratte = data;
                this.tratte.fermateA.sort((a, b) => a.ora.localeCompare(b.ora));
                this.tratte.fermateR.sort((a, b) => a.ora.localeCompare(b.ora));
              });
          });
      });
  }

  clickPersona($event: MouseEvent, verso, idFermata, idPersona) {
    const today = new Date();
    const date = new Date(this.corse[this.pageIndex].data);
    let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    let yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    dd = String(date.getDate()).padStart(2, '0');
    mm = String(date.getMonth() + 1).padStart(2, '0'); // January is 0!
    yyyy = date.getFullYear();
    const curdate = yyyy + '/' + mm + '/' + dd;

    if (cur.localeCompare(curdate) === 0) {
      if (verso == 0) {
        this.lineaService.updateprenotazioneAccompagnatore(this.tratte.fermateA[idFermata].persone[idPersona].id_bambino, this.tratte.fermateA[idFermata].id_fermata, this.corse[this.pageIndex].data, 0).subscribe(
          (data) => {if (this.tratte.fermateA[idFermata].persone[idPersona].selected === false) {
            this.tratte.fermateA[idFermata].persone[idPersona].selected = true;
          } else {
            this.tratte.fermateA[idFermata].persone[idPersona].selected = false;
          }}
        );
      } else {
        this.lineaService.updateprenotazioneAccompagnatore(this.tratte.fermateR[idFermata].persone[idPersona].id_bambino, this.tratte.fermateR[idFermata].id_fermata, this.corse[this.pageIndex].data, 1).subscribe(
          (data) => {
            if (this.tratte.fermateR[idFermata].persone[idPersona].selected === false) {
              this.tratte.fermateR[idFermata].persone[idPersona].selected = true;
            } else {
              this.tratte.fermateR[idFermata].persone[idPersona].selected = false;
            }
          }
        );
      }
    }
  }

  selezionaPersona(verso: number, idFermata: number, idPersona: number) {
    if (verso == 0) {
      if (this.tratte.fermateA[idFermata].persone[idPersona].selected === true) {
        return 'personaSelezionata';
      }
    } else {
      if (this.tratte.fermateR[idFermata].persone[idPersona].selected === true) {
        return 'personaSelezionata';
      }
    }
  }

  personeOrdinateByNome(verso: number, idFermata: number) {
    if (verso == 0) {
      return this.tratte.fermateA[idFermata].persone.sort((a, b) => a.nome.localeCompare(b.nome));
    } else {
      return this.tratte.fermateR[idFermata].persone.sort((a, b) => a.nome.localeCompare(b.nome));
    }
  }

  fermateOrdinateByOra(verso: number) {
    if (verso == 0) {
      return this.tratte.fermateA.sort((a, b) => a.ora.localeCompare(b.ora));
    } else {
      return this.tratte.fermateR.sort((a, b) => a.ora.localeCompare(b.ora));
    }
  }

  selezionaLineaMenu(idLinea: number) {
    this.lineaSelezionataMenu = idLinea;
    let promiseCorsa = fetch('http://localhost:8080/utility/corse/' + this.nomiLinee[this.lineaSelezionataMenu].nome, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.corse = data;
        return;
      }).then(() => {
        this.length = this.corse.length;  // Poichè corse ha Andata e Ritorno
        this.corse.sort((a, b) => a.data.localeCompare(b.data));
        let j;
        let date;
        const today = new Date();
        const dd = String(today.getDate()).padStart(2, '0');
        const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
        const yyyy = today.getFullYear();
        const cur = yyyy + '/' + mm + '/' + dd;
        const curdate = new Date(cur).getTime();
        for (j = 0; j < this.corse.length; j++) {
          date = new Date(this.corse[j].data).getTime();
          if (date - curdate >= 0) {
            break;
          }
        }
        this.pageIndex = j;
      })
      .then(() => {
        let promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('access_token')
          }
        })
          .then((data) => {
            return data.json();
          }).then((data) => {
            this.tratte = data;
          });
      });
  }

  selezionaCorsaPaginator(pageEvent: PageEvent) {
    this.pageIndex = pageEvent.pageIndex;
    let promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.tratte = data;
      });
    return this.pageEvent;
  }

  logout($event: MouseEvent) {
    this.authenticationService.logout();
    this.alertService.success('Logout effettuato', true);
    this.router.navigate(['/login']);
  }

  getBambini() {
    this.lineaService.getbambini().subscribe(
      val => {
        this.bambini = val;
      }
    );
  }


  inserisciBambinoNonPrenotato($event: MouseEvent, id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
    const today = new Date();
    const date = new Date(this.corse[this.pageIndex].data);
    let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    let yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    dd = String(date.getDate()).padStart(2, '0');
    mm = String(date.getMonth() + 1).padStart(2, '0'); // January is 0!
    yyyy = date.getFullYear();
    const curdate = yyyy + '/' + mm + '/' + dd;

    if (cur.localeCompare(curdate) === 0) {
      this.lineaService.inserisciPrenotazioneRitardataAccompagnatore(id_bambino, linea, id_fermata, verso, data).subscribe();
    }
    return;
  }

  OnClickOpenForm() {
    if (this.openDateForm == false) {
      this.openDateForm = true;
    } else {
      this.openDateForm = false;
    }
  }


  selezionaCorsaCalendario() {
    const pageDate = new Date(this.corse[this.pageIndex].data).setUTCHours(0o0, 0o0, 0o0, 0o0);
    const date = new Date(this.data).setUTCHours(0o0, 0o0, 0o0, 0o0);
    const diff = (date - pageDate) / (24 * 3600 * 1000);

    if ((this.pageIndex + diff) < 0 || (this.pageIndex + diff) > 364) {
      this.alertService.error('Corsa non esistente!');
      return;
    } else {
      this.pageIndex += diff;
      let promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('access_token')
        }
      })
        .then((data) => {
          return data.json();
        }).then((data) => {
          this.tratte = data;
        });
      this.openDateForm = false;
      return;
    }
  }

  cambiaSezione(url: string) {
    this.router.navigate(['/' + url]);
  }

  AzzeraContatore($event) {
    if ($event.index == 2) {     //SE SI AGGIUNGONO ALTRE MAT-TAB VA CAMBIATO IL NUMERO
      this.notifica.count = 0;
      this.userService.azzeraNotifica(localStorage.getItem('username')).subscribe();
    }
  }

  presavisione($event: MouseEvent, data: string, verso: string, utente: string) {
    this.p = new presaVisione(data, verso, utente);
    this.userService.presaVisione(this.p).subscribe();
  }

  downloadJSONpresenze(format: string, verso: number) {
    if (format === 'json') {
      if (verso === 0) {
        const blob = new Blob([JSON.stringify(this.tratte.fermateA, null, '  ')], {type: 'text/json'});
        saveAs(blob, 'andata.json');
      } else {
        const blob = new Blob([JSON.stringify(this.tratte.fermateR, null, '  ')], {type: 'text/json'});
        saveAs(blob, 'ritorno.json');
      }
    } else if (format === 'csv') {
      if (verso === 0) {
        let csv = AttendanceComponent.ConvertToCSV(JSON.stringify(this.tratte.fermateA));
        saveAs(new Blob([csv]), 'andata.csv');
      } else {
        let csv = AttendanceComponent.ConvertToCSV(JSON.stringify(this.tratte.fermateR));
        saveAs(new Blob([csv]), 'ritorno.csv');
      }
    }
  }

  static ConvertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';

    for (var i = 0; i < array.length; i++) {
      var line = '';
      for (var index in array[i]) {
        if (line != '' && index != 'persone') {
          line += ',';
        }
        if (typeof array[i][index] != 'object') {
          line += array[i][index];
        } else {
          if (array[i][index].length > 0) {
            line += ',';
          }
          for (var j = 0; j < array[i][index].length; j++) {
            alert(array[i][index][j].nome + ' ' + array[i][index][j].cognome);
            line += array[i][index][j].nome + ' ' + array[i][index][j].cognome;
            if (j < array[i][index].length - 1) {
              line += ', ';
            }
          }
        }
      }
      str += line + '\r\n';
    }
    return str;
  }
}

