import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';
import {LineaService} from './_services/linea.service';
import {AlertService, AuthenticationService, UserService} from './_services';
import {Router} from '@angular/router';
import {Bambino} from './_models/bambino';
import {nomeLinea} from './_models/nomeLinea';
import {CorsaNew} from './_models/corsaNew';
import {TrattaNew} from './_models/trattaNew';
import {Disponibilita, Ruolo} from './_models';
import {Notifica} from './_models/notifica';
import {WebSocketService} from './_services/websocket.service';
import {presaVisione} from './_models/presaVisione';
import {saveAs} from 'file-saver';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {checkRuoli} from './_models/checkRuoli';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {

  changePasswordForm: FormGroup;
  password0_blur=false;
  password1_blur=false;
  password2_blur=false;
  messaggio: string;
  submitted0 = false;
  submitted1 = false;
  submitted2 = false;

  constructor(private webSocketService: WebSocketService, private lineaService: LineaService, private userService: UserService, private authenticationService: AuthenticationService, private alertService: AlertService, private router: Router, private formBuilder: FormBuilder, private modalService: NgbModal) {
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
      this.notifica = new Notifica(this.x, '', '', '', '', '', 0, false);
      if (this.x != 0) {
        this.notifications.push(this.notifica);
      }
    }).then(() => {
      const promiseNotificheOffline = fetch('http://localhost:8080/utility/notificheoffline/' + localStorage.getItem('username'), {
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + localStorage.getItem('access_token')
        }
      })
        .then((data) => {
          return data.json();
        }).then((data) => {
          this.notifications = data;
        });
    });


// Open connection with server socket
    const stompClient = this.webSocketService.connect();
    stompClient.connect({}, frame => {

      // Subscribe to notification topic
      stompClient.subscribe('/user/' + localStorage.getItem('username') + '/queue/reply', notifications => {

        // Update notifications attribute with the recent messsage sent from the server
        this.notifica = JSON.parse(notifications.body);
        this.notifications.push(this.notifica);

        const promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + localStorage.getItem('access_token')
          }
        })
          .then((data) => {
            return data.json();
          }).then((data) => {
            this.tratte = data;
          });
      });
    });
  }
  title = 'Pedibus';

  bambini: Bambino[] = [];
  nomiLinee: nomeLinea[] = [];
  corse: CorsaNew[] = [];
  tratte = {} as TrattaNew;
  data: string;


  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;
  openDateForm = false;
  ruoli: Ruolo[] = [];
  checkRuoli: checkRuoli = new checkRuoli(undefined, undefined, undefined, undefined);

  notifications: Notifica[] = [];
  notifica: Notifica;
  x: number;
  p: presaVisione;

  turni: Disponibilita[] = [];

  static ConvertToCSV(objArray) {
    const array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    let str = '';

    for (let i = 0; i < array.length; i++) {
      let line = '';
      for (const index in array[i]) {
        if (line != '' && index != 'persone') {
          line += ',';
        }
        if (typeof array[i][index] != 'object') {
          line += array[i][index];
        } else {
          if (array[i][index].length > 0) {
            line += ',';
          }
          for (let j = 0; j < array[i][index].length; j++) {
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

  ngOnInit(): void {
    if (localStorage.getItem('access_token') == null) {
      this.alertService.error('Utente non ancora loggato!', true);
      this.router.navigate(['/login']);
    }

    this.changePasswordForm = this.formBuilder.group({
      password0: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(12)]],
      password1: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(12)]],
      password2: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(12)]]
    }, {
      validators: [MustMatch('password1', 'password2')]
    });

    const promiseRuoli = fetch('http://localhost:8080/utility/ruoli', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.ruoli = data;
        return;
      }).then((data) => {
        for (const r of this.ruoli) {
          this.checkRuoli.checkAccompagnatore = false;
          this.checkRuoli.checkAdmin = false;
          this.checkRuoli.checkSystemAdmin = false;
          this.checkRuoli.checkUser = false;
          if (r.ruolo.localeCompare('admin') == 0) {
            this.checkRuoli.checkAdmin = true;
          } else if (r.ruolo.localeCompare('system-admin') == 0) {
            this.checkRuoli.checkSystemAdmin = true;
          } else if (r.ruolo.localeCompare('accompagnatore') == 0) {
            this.checkRuoli.checkAccompagnatore = true;
          } else if(r.ruolo.localeCompare('user') == 0){
            this.checkRuoli.checkUser = true;
          }
        }
      });

    const promiseBambini = fetch('http://localhost:8080/utility/children', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.bambini = data;
      });

    const promiseLinea = fetch('http://localhost:8080/lines', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.nomiLinee = data;
        return;
      }).then(() => {
        const promiseCorsa = fetch('http://localhost:8080/utility/corse/' + this.nomiLinee[0].nome, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + localStorage.getItem('access_token')
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
            const promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[0].nome + '/' + this.corse[this.pageIndex].data, {
              headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + localStorage.getItem('access_token')
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


    const promiseTurni = fetch('http://localhost:8080/utility/turni', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.turni = data;
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
          data => {
            if (this.tratte.fermateA[idFermata].persone[idPersona].selected === false) {
              this.tratte.fermateA[idFermata].persone[idPersona].selected = true;
            } else {
              this.tratte.fermateA[idFermata].persone[idPersona].selected = false;
            }
            this.alertService.success('Presenza del bambino registrata con successo!', true);
            // this.router.navigate(['/simpleuser']);
          },
          error => {
            this.alertService.error('Impossibile registrare la presenza del bambino!');
          });
      } else {
        this.lineaService.updateprenotazioneAccompagnatore(this.tratte.fermateR[idFermata].persone[idPersona].id_bambino, this.tratte.fermateR[idFermata].id_fermata, this.corse[this.pageIndex].data, 1).subscribe(
          data => {
            if (this.tratte.fermateR[idFermata].persone[idPersona].selected === false) {
              this.tratte.fermateR[idFermata].persone[idPersona].selected = true;
            } else {
              this.tratte.fermateR[idFermata].persone[idPersona].selected = false;
            }
            this.alertService.success('Presenza del bambino registrata con successo!', true);
            // this.router.navigate(['/simpleuser']);
          },
          error => {
            this.alertService.error('Impossibile registrare la presenza del bambino!');
          });
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
    const promiseCorsa = fetch('http://localhost:8080/utility/corse/' + this.nomiLinee[this.lineaSelezionataMenu].nome, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
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
        const promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + localStorage.getItem('access_token')
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
    const promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
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
      this.lineaService.inserisciPrenotazioneRitardataAccompagnatore(id_bambino, linea, id_fermata, verso, data).subscribe(
        data => {
          this.alertService.success('Presenza del bambino registrata con successo!', true);
          // this.router.navigate(['/simpleuser']);
          const promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[0].nome + '/' + this.corse[this.pageIndex].data, {
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Bearer ' + localStorage.getItem('access_token')
            }
          })
            .then((data) => {
              return data.json();
            }).then((data) => {
              this.tratte = data;
              this.tratte.fermateA.sort((a, b) => a.ora.localeCompare(b.ora));
              this.tratte.fermateR.sort((a, b) => a.ora.localeCompare(b.ora));
            });
        },
        error => {
          this.alertService.error('Impossibile registrare la presenza del bambino!');
        });
    }
    this.alertService.error('Impossibile registrare la presenza del bambino poichè non è la data corrente');
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
      const promiseTratte = fetch('http://localhost:8080/utility/reservations/' + this.nomiLinee[this.lineaSelezionataMenu].nome + '/' + this.corse[this.pageIndex].data, {
        headers: {
          'Content-Type': 'application/json',
          Authorization: 'Bearer ' + localStorage.getItem('access_token')
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
    if ($event.index == 2) {     // SE SI AGGIUNGONO ALTRE MAT-TAB VA CAMBIATO IL NUMERO //old=2 new=3
      this.notifica.count = 0;
      this.userService.azzeraNotifica(localStorage.getItem('username')).subscribe();
    }
  }

  presavisione($event: MouseEvent, data: string, verso: string, utente: string, ind: number) {
    this.p = new presaVisione(data, verso, utente);
    this.userService.presaVisione(this.p, ind).subscribe(
      data => {
        this.alertService.success('Turno consolidato con successo!', true);
      },
      error => {
        this.alertService.error('Impossibile consolidare il turno!');
      });
    const element = document.getElementById('myBtn' + ind) as HTMLInputElement;
    element.disabled = true;
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
        const csv = AttendanceComponent.ConvertToCSV(JSON.stringify(this.tratte.fermateA));
        saveAs(new Blob([csv]), 'andata.csv');
      } else {
        const csv = AttendanceComponent.ConvertToCSV(JSON.stringify(this.tratte.fermateR));
        saveAs(new Blob([csv]), 'ritorno.csv');
      }
    }
  }

  cancellaNotifiche($event: MouseEvent) {
    const promiseCancellaNotifiche = fetch('http://localhost:8080/utility/cancellaNotifiche/' + localStorage.getItem('username'), {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.notifications = data;
      });
  }


  cancellaTurno($event: MouseEvent, data: string, verso: string, username: string, ind: number) {

    this.userService.cancellaTurno(new presaVisione(data, verso, username)).subscribe(
      data => {
        this.turni.splice(ind, 1);
        this.alertService.success('Turno cancellato con successo!', true);
        this.router.navigate(['/attendance']);
        // this.router.navigate(['/simpleuser']);
      },
      error => {
        this.alertService.error('Impossibile cancellare il turno!');
      });

 /*   const promiseTurni = fetch('http://localhost:8080/utility/turni', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.turni = data;
      });*/
    // confirm('test' + ' ' + data + ' ' + verso + ' ' + username);
  }

  onBlurChangePassword(field: number) {
    if(field==0){
      this.password0_blur = true;
    } else if(field==1){
      this.password1_blur = true;
    } else if(field==2){
      this.password2_blur = true;
    }
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }

  changePasswordSubmit() {
    this.submitted2 = true;
    //if (this.changePasswordForm.invalid) {
    //  return;
    //}
    // stop here if form is invalid

    this.userService.cambiaPassword(this.changePasswordForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.changePasswordForm.reset();
          this.alertService.success('Password cambiata con successo!', true);
          //this.router.navigate(['/simpleuser']);
          this.modalService.dismissAll();
        },
        error => {
          this.alertService.error('Cambio Password fallito!');
          //this.messaggio='Cambio Password fallito!';
        });
  }


  getUtente() {
    return localStorage.getItem("username");
  }
}


function MustMatch(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
    const matchingControl = formGroup.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      // return if another validator has already found an error on the matchingControl
      return;
    }

    // set error on matchingControl if validation fails
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({mustMatch: true});
    } else {
      matchingControl.setErrors(null);
    }
  };
}
