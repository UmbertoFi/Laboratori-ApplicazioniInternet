import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';
import {LineaService} from './_services/linea.service';
import {AlertService, AuthenticationService, UserService} from './_services';
import {Router} from '@angular/router';
import {Bambino} from './_models/bambino';
import {nomeLinea} from './_models/nomeLinea';
import {CorsaNew} from './_models/corsaNew';
import {TrattaNew} from './_models/trattaNew';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';
// @ts-ignore
import {UtenteNew} from './_models/utenteNew';
import {Disponibilita} from './_models/disponibilita';
import {Ruolo} from './_models';
import {Notifica} from './_models/notifica';
import {WebSocketService} from './_services/websocket.service';
import {presaVisione} from './_models/presaVisione';
import {checkRuoli} from './_models/checkRuoli';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  title = 'Pedibus';

  nomiLinee: nomeLinea[] = [];
  nomiLineePerAdmin: String[] = [];
  corse: CorsaNew[] = [];
  tratte = {} as TrattaNew;
  figli: Bambino[] = [];
  // aggiungiBambinoForm: FormGroup;
  // submitted: boolean = false;
  promuoviUserForm: FormGroup;
  utenti: UtenteNew[] = [];
  utentiEsclusoMe: UtenteNew[] = [];
  trovaDisponibilitaForm: FormGroup;
  disponibilita: Disponibilita[] = [];
  consolidaTurnoForm: FormGroup;
  d: Disponibilita;
  ruoli: Ruolo[] = [];
  checkRuoli: checkRuoli = new checkRuoli(undefined, undefined, undefined, undefined);
  submitted0 = false;
  submitted1 = false;
  submitted2 = false;


  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;

  notifications: Notifica[] = [];
  notifica: Notifica;
  x: number;
  p: presaVisione;
  username_blur = false;
  linea_blur = false;
  azione_blur = false;
  linea2_blur = false;
  data_blur = false;
  verso_blur = false;
  username2_blur = false;

  changePasswordForm: FormGroup;
  password0_blur = false;
  password1_blur = false;
  password2_blur = false;
  messaggio: string;

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

    /* this.aggiungiBambinoForm = this.formBuilder.group({
      nome: ['', Validators.required],
      cognome: ['', Validators.required]
    }); */

    this.promuoviUserForm = this.formBuilder.group({
      username: ['', Validators.required],
      linea: ['', Validators.required],
      azione: ['', Validators.required]
    });

    this.trovaDisponibilitaForm = this.formBuilder.group({
      linea: ['', Validators.required],
      data: ['', Validators.required],
      verso: ['', Validators.required]
    });

    this.consolidaTurnoForm = this.formBuilder.group({
      username: ['', Validators.required]
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

    const promiseUtenti = fetch('http://localhost:8080/users', {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.utenti = data;
        return data;
      }).then((data) => {
        for (const u of this.utenti) {
          if (u.username.localeCompare(localStorage.getItem('username')) != 0) {
            this.utentiEsclusoMe.push(u);
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

    const promiseLineaPerAdmin = fetch('http://localhost:8080/utility/adminlines/' + localStorage.getItem('username'), {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.nomiLineePerAdmin = data;
        return;
      });
  }

  /* clickPersona($event: MouseEvent, verso, idFermata, idPersona) {
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
        this.lineaService.updateprenotazione(this.tratte.fermateA[idFermata].persone[idPersona].id_bambino, this.tratte.fermateA[idFermata].id_fermata, this.corse[this.pageIndex].data, 0).subscribe();
      } else {
        if (this.tratte.fermateR[idFermata].persone[idPersona].selected === false) {
          this.tratte.fermateR[idFermata].persone[idPersona].selected = true;
        } else {
          this.tratte.fermateR[idFermata].persone[idPersona].selected = false;
        }
        this.lineaService.updateprenotazione(this.tratte.fermateR[idFermata].persone[idPersona].id_bambino, this.tratte.fermateR[idFermata].id_fermata, this.corse[this.pageIndex].data, 1).subscribe();
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
  } */

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

  onBlurChangePassword(field: number) {
    if (field == 0) {
      this.password0_blur = true;
    } else if (field == 1) {
      this.password1_blur = true;
    } else if (field == 2) {
      this.password2_blur = true;
    }
  }

  /* getBambini() {
    this.lineaService.getbambini().subscribe(
      val => {
        this.bambini = val;
      }
    );
  } */


  /*inserisciFiglio($event: MouseEvent, id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
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

    if (cur.localeCompare(curdate) < 0) {
      this.lineaService.inserisciPrenotazioneRitardata(id_bambino, linea, id_fermata, verso, data).subscribe();
    }
    return;
  }*/

  /* onSubmit() {
    this.submitted = true;

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
        },
        error => {
          this.alertService.error('Inserimento bambino fallito!');
        });
  } */

  onSubmitPromuovi() {
    this.submitted0 = true;
    // stop here if form is invalid
    if (this.promuoviUserForm.invalid) {
      return;
    }

    this.userService.modificaRuolo(this.promuoviUserForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Utente promosso/declassato con successo!', true);
          // this.router.navigate(['/simpleuser']);
        },
        error => {
          this.alertService.error('Utente non promosso/declassato correttamente!');
        });
  }

  onSubmitTrovaDisponibilita() {
    this.submitted1 = true;
    // stop here if form is invalid
    if (this.trovaDisponibilitaForm.invalid) {
      return;
    }

    const promiseDisponibilita = fetch('http://localhost:8080/utility/disponibilita/' + this.trovaDisponibilitaForm.value.linea + '/' + this.trovaDisponibilitaForm.value.data + '/' + this.trovaDisponibilitaForm.value.verso, {
      headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + localStorage.getItem('access_token')
      }
    }).then( (response) => {if (response.status >= 400 && response.status < 500) {
      throw new Error('errore throw');
    } else {
      return response;
    }})
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.disponibilita = data;
        this.d = new Disponibilita(this.trovaDisponibilitaForm.value.linea, this.trovaDisponibilitaForm.value.data, this.trovaDisponibilitaForm.value.verso, '');
        this.alertService.success('Disponibilità aggiornate per la corsa scelta con successo!', true);
        // this.router.navigate(['/simpleuser']);
        return;
      })
      .catch((error) => {
        this.alertService.error('Errore aggiornamento disponibilità per la corsa scelta!');
        return;
      });


  }

  onSubmitConsolidaTurno() {
    this.submitted2 = true;
    // stop here if form is invalid
    if (this.consolidaTurnoForm.invalid) {
      return;
    }

    this.d.username = this.consolidaTurnoForm.value.username;

    this.userService.consolidaTurno(this.d)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Turno assegnato per la corsa scelta con successo!', true);
          // this.router.navigate(['/simpleuser']);
        },
        error => {
          this.alertService.error('Errore assegnamento per la corsa scelta!');
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

  consolidaTurnoReset() {
    if (this.disponibilita != undefined) {
      this.consolidaTurnoForm.reset();
      while (this.disponibilita.pop()) {

      }
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

  onBlur(field: number) {
    if (field == 0) {
      this.username_blur = true;
    } else if (field == 1) {
      this.linea_blur = true;
    } else if (field == 2) {
      this.azione_blur = true;
    } else if (field == 3) {
      this.linea2_blur = true;
    } else if (field == 4) {
      this.data_blur = true;
    } else if (field == 5) {
      this.verso_blur = true;
    } else if (field == 6) {
      this.username2_blur = true;
    }
  }

  pulisciDatabase() {
    this.userService.pulisciDatabase().subscribe(
      data => {
        this.alertService.success('Database pulito con successo!', true);
        // this.router.navigate(['/simpleuser']);
      },
      error => {
        this.alertService.error('Impossibile pulire il database!');
      });
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
        this.notifica.count = 0;
        this.userService.azzeraNotifica(localStorage.getItem('username')).subscribe();
        this.notifications = data;
      });
  }

  getUtente() {
    return localStorage.getItem('username');
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
