import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';
import {LineaService} from './_services/linea.service';
import {AlertService, AuthenticationService, UserService} from './_services';
import {Router} from '@angular/router';
import {Bambino} from './_models/bambino';
import {Flag} from './_models/flag';
import {nomeLinea} from './_models/nomeLinea';
import {CorsaNew} from './_models/corsaNew';
import {TrattaNew} from './_models/trattaNew';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
import {Prenotazione, Ruolo} from './_models';
import {WebSocketService} from './_services/websocket.service';
import {Notifica} from './_models/notifica';
import {presaVisione} from './_models/presaVisione';
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';
import {checkRuoli} from './_models/checkRuoli';


@Component({
  selector: 'app-simpleuser',
  templateUrl: './simpleuser.component.html',
  styleUrls: ['./simpleuser.component.css']
})
export class SimpleuserComponent implements OnInit {
  title = 'Pedibus';

  nomiLinee: nomeLinea[] = [];
  corse: CorsaNew[] = [];
  tratte: TrattaNew;
  figli: Bambino[] = [];
  aggiungiBambinoForm: FormGroup;
  submitted0 = false;
  submitted1 = false;
  submitted2 = false;
  candidatiAccompagnatoreForm: FormGroup;
  data: string;
  openDateForm = false;
  ruoli: Ruolo[] = [];
  checkRuoli: checkRuoli = new checkRuoli(undefined, undefined, undefined, undefined);


  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;


  notifications: Notifica[] = [];
  notifica: Notifica;
  x: number;
  p: presaVisione;
  nome_blur = false;
  cognome_blur = false;
  linea_blur = false;
  data_blur = false;
  verso_blur = false;

  changePasswordForm: FormGroup;
  password0_blur = false;
  password1_blur = false;
  password2_blur = false;
  messaggio: string;

  constructor(
    private webSocketService: WebSocketService, private lineaService: LineaService, private userService: UserService, private authenticationService: AuthenticationService, private alertService: AlertService, private router: Router, private formBuilder: FormBuilder, private modalService: NgbModal) {

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
      const promiseTratte = fetch('http://localhost:8080/utility/notificheoffline/' + localStorage.getItem('username'), {
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
      });
    });
  }

  ngOnInit(): void {
    if (localStorage.getItem('access_token') == null) {
      this.alertService.error('Utente non ancora loggato!', true);
      this.router.navigate(['/login']);
    }

    this.aggiungiBambinoForm = this.formBuilder.group({
      nome: ['', Validators.required],
      cognome: ['', Validators.required]
    });

    this.candidatiAccompagnatoreForm = this.formBuilder.group({
      linea: ['', Validators.required],
      data: ['', Validators.required],
      verso: ['', Validators.required]
    });

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
        this.checkRuoli.checkAccompagnatore = false;
        this.checkRuoli.checkAdmin = false;
        this.checkRuoli.checkSystemAdmin = false;
        this.checkRuoli.checkUser = false;
        for (const r of this.ruoli) {
          if (r.ruolo.localeCompare('admin') == 0) {
            this.checkRuoli.checkAdmin = true;
          } else if (r.ruolo.localeCompare('system-admin') == 0) {
            this.checkRuoli.checkSystemAdmin = true;
          } else if (r.ruolo.localeCompare('accompagnatore') == 0) {
            this.checkRuoli.checkAccompagnatore = true;
          } else if (r.ruolo.localeCompare('user') == 0) {
            this.checkRuoli.checkUser = true;
          }
        }
      });

    const promiseFigli = fetch('http://localhost:8080/utility/children/' + localStorage.getItem('username'), {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.figli = data;
      });

    /* let promiseBambini = fetch('http://localhost:8080/utility/children', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.bambini = data;
      }); */

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
        if (this.tratte.fermateA[idFermata].persone[idPersona].selected === false) {
          this.tratte.fermateA[idFermata].persone[idPersona].selected = true;
        } else {
          this.tratte.fermateA[idFermata].persone[idPersona].selected = false;
        }
        this.lineaService.updateprenotazione(this.tratte.fermateA[idFermata].persone[idPersona].id_bambino, this.tratte.fermateA[idFermata].id_fermata, this.corse[this.pageIndex].data, 0).subscribe(
          data => {
            this.alertService.success('Bambino prenotato con successo!', true);
            // this.router.navigate(['/simpleuser']);
          },
          error => {
            this.alertService.error('Impossibile prenotare il bambino!');
          });
      } else {
        if (this.tratte.fermateR[idFermata].persone[idPersona].selected === false) {
          this.tratte.fermateR[idFermata].persone[idPersona].selected = true;
        } else {
          this.tratte.fermateR[idFermata].persone[idPersona].selected = false;
        }
        this.lineaService.updateprenotazione(this.tratte.fermateR[idFermata].persone[idPersona].id_bambino, this.tratte.fermateR[idFermata].id_fermata, this.corse[this.pageIndex].data, 1).subscribe(
          data => {
            this.alertService.success('Bambino prenotato con successo!', true);
            // this.router.navigate(['/simpleuser']);
          },
          error => {
            this.alertService.error('Impossibile prenotare bambino!');
          });
      }
    }
    this.alertService.error('Impossibile prenotare bambino poichè il bus è già passato!');
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

  /* getBambini() {
    this.lineaService.getbambini().subscribe(
      val => {
        this.bambini = val;
      }
    );
  } */


  inserisciFiglio($event: MouseEvent, id_bambino: number, linea: string, id_fermata: number, verso: number, data: string, ora: string) {
    const today = new Date();
    const date = new Date(this.corse[this.pageIndex].data);
    let dd = String(today.getDate()).padStart(2, '0');
    let mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    let yyyy = today.getFullYear();
    const actual_hour = today.getHours();
    const actual_min = today.getMinutes();
    const cur = yyyy + '/' + mm + '/' + dd;
    dd = String(date.getDate()).padStart(2, '0');
    mm = String(date.getMonth() + 1).padStart(2, '0'); // January is 0!
    yyyy = date.getFullYear();
    const h = parseInt(ora.split(':')[0]);
    const min = parseInt(ora.split(':')[1]);
    const curdate = yyyy + '/' + mm + '/' + dd;

    if (cur.localeCompare(curdate) < 0 || (cur.localeCompare(curdate) == 0 && actual_hour < h) || (cur.localeCompare(curdate) == 0 && actual_hour == h && actual_min < min)) {
      this.lineaService.inserisciPrenotazioneRitardata(id_bambino, linea, id_fermata, verso, data).subscribe(
        data => {
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
          this.alertService.success('Bambino prenotato con successo!', true);
          // this.router.navigate(['/simpleuser']);
        },
        error => {
          this.alertService.error('Impossibile prenotare il bambino!');
        });
      /* if (confirm('Vuoi prenotare la stessa corsa per un intero anno?')) {
        date = new Date(this.corse[this.pageIndex].data);
        for (let i = this.pageIndex; i < this.length - 1; i++) {
          date.setDate(date.getDate() + 1);
          let dd = String(date.getDate()).padStart(2, '0');
          let mm = String(date.getMonth() + 1).padStart(2, '0'); // January is 0!
          let yyyy = date.getFullYear();
          data = yyyy + '-' + mm + '-' + dd;
          this.lineaService.inserisciPrenotazioneRitardata(id_bambino, linea, id_fermata, verso, data).subscribe();
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
        }
        this.alertService.success('Bambino prenotato con successo per un anno intero!', true);
      } */
    }
    else
      this.alertService.error('Impossibile prenotare bambino poichè il bus è già passato');
    return;
  }

  onSubmit() {
    this.submitted0 = true;

    // stop here if form is invalid
    if (this.aggiungiBambinoForm.invalid) {
      return;
    }
    this.userService.aggiungiBambino(this.aggiungiBambinoForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Inserimento bambino eseguito con successo!', true);
           // this.router.navigate(['/simpleuser']);
          const promiseFigli = fetch('http://localhost:8080/utility/children/' + localStorage.getItem('username'), {
            headers: {
              'Content-Type': 'application/json',
              Authorization: 'Bearer ' + localStorage.getItem('access_token')
            }
          })
            .then((data) => {
              return data.json();
            }).then((data) => {
              this.figli = data;
            });
        },
        error => {
          this.alertService.error('Inserimento bambino fallito!');
        });
  }

  onSubmitAccompagnatore() {

    this.submitted1 = true;

    // stop here if form is invalid
    if (this.candidatiAccompagnatoreForm.invalid) {
      return;
    }

    this.userService.candidatiAccompagnatore(this.candidatiAccompagnatoreForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Candidatura come accompagnatore eseguita con successo!', true);
          // this.router.navigate(['/simpleuser']);
        },
        error => {
          this.alertService.error('Candidatura come accompagnatore fallita!');
        });
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
      if(isNaN(this.pageIndex)){
        this.pageIndex=0;
        return;
      }
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
    if ($event.index == 2) {     // SE SI AGGIUNGONO ALTRE MAT-TAB VA CAMBIATO IL NUMERO
      this.notifica.count = 0;
      this.userService.azzeraNotifica(localStorage.getItem('username')).subscribe();
    }
  }

  presavisione($event: MouseEvent, data: string, verso: string, utente: string, ind: number) {
    this.p = new presaVisione(data, verso, utente);
    this.userService.presaVisione(this.p, ind).subscribe(
        data => {
          // this.trovato_accompagnatore = data;
          if (this.notifications[ind].accompagnatore) {
            this.alertService.success('Turno consolidato con successo!', true);
          } else {
            this.alertService.success('Turno consolidato con successo! Riloggare cortesemente poichè sono cambiati i privilegi!', true);
            this.authenticationService.logout();
            this.router.navigate(['/login']);
          }
          },
        error => {
          this.alertService.error('Impossibile consolidare il turno!');
        });
    const element = document.getElementById('myBtn' + ind) as HTMLInputElement;
    element.disabled = true;
  }

  rimuoviPrenotazione($event: MouseEvent, nomeLinea: string, data: string, id_bambino: number, id_fermata: number, verso: number) {
    this.userService.checkChildren(id_bambino).subscribe(
      (res) => {if (confirm('Desideri cancellare la prenotazione?')) {
        let v;
        if (verso == 0) {
          v = 'A';
        } else {
          v = 'R';
        }
        this.userService.rimuoviPrenotazione(nomeLinea, data, id_bambino + '_' + data + '_' + v).subscribe(
          data => {
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
            this.alertService.success('Prenotazione rimossa con successo!', true);
          },
          error => {
            this.alertService.error('Impossibile rimuovere la prenotazione!');
          });
      }});
  }

  onBlur(field: number) {
    if (field == 0) {
      this.nome_blur = true;
    } else if (field == 1) {
      this.cognome_blur = true;
    } else if (field == 2) {
      this.linea_blur = true;
    } else if (field == 3) {
      this.data_blur = true;
    } else if (field == 4) {
      this.verso_blur = true;
    }
  }


  onBlurChangePassword(field: number) {
    if (field == 0) {
      this.password0_blur = true;
    } else if (field == 1) {
      this.password1_blur = true;
    } else if (field == 2) {
      this.password2_blur = true;
    }
  }
  cancellaNotifiche($event: MouseEvent) {
    const promiseTratte = fetch('http://localhost:8080/utility/cancellaNotifiche/' + localStorage.getItem('username'), {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.notifica.count=0;
        this.userService.azzeraNotifica(localStorage.getItem('username')).subscribe();
        this.notifications = data;
      });
  }
  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }


  changePasswordSubmit() {
    this.submitted2 = true;
    // if (this.changePasswordForm.invalid) {
    //  return;
    // }
    // stop here if form is invalid

    this.userService.cambiaPassword(this.changePasswordForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.changePasswordForm.reset();
          this.alertService.success('Password cambiata con successo!', true);
          // this.router.navigate(['/simpleuser']);
          this.modalService.dismissAll();
          },
        error => {
          this.alertService.error('Cambio Password fallito!');
          // this.messaggio='Cambio Password fallito!';
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
