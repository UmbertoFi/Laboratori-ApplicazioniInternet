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
import {UtenteNew} from './_models/UtenteNew';
import {Disponibilita} from './_models/disponibilita';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  title = 'Pedibus';

  nomiLinee: nomeLinea[];
  corse: CorsaNew[];
  tratte: TrattaNew;
  figli: Bambino[];
  aggiungiBambinoForm: FormGroup;
  submitted: boolean = false;
  promuoviUserForm: FormGroup;
  utenti: UtenteNew[];
  trovaDisponibilitaForm: FormGroup;
  disponibilita: Disponibilita[];
  consolidaTurnoForm: FormGroup;


  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;


  constructor(private lineaService: LineaService, private userService: UserService, private authenticationService: AuthenticationService, private alertService: AlertService, private router: Router, private formBuilder: FormBuilder) {
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
      disponibilita: ['', Validators.required]
    });

    let promiseUtenti = fetch('http://localhost:8080/users', {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
      }
    })
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.utenti = data;
      });

    let promiseFigli = fetch('http://localhost:8080/utility/children/' + localStorage.getItem('username'), {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + localStorage.getItem('access_token')
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

  /* getBambini() {
    this.lineaService.getbambini().subscribe(
      val => {
        this.bambini = val;
      }
    );
  } */


  inserisciFiglio($event: MouseEvent, id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
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
  }

  onSubmit() {
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
  }

  onSubmitPromuovi() {

    // stop here if form is invalid
    /* if (this.aggiungiBambinoForm.invalid) {
      return;
    } */

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
    // stop here if form is invalid
    /* if (this.aggiungiBambinoForm.invalid) {
      return;
    } */

    this.userService.trovaDisponibilita(this.trovaDisponibilitaForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.disponibilita.push(data);
          this.alertService.success('Disponibilità aggiornate per la corsa scelta con successo!', true);
          // this.router.navigate(['/simpleuser']);
        },
        error => {
          this.alertService.error('Errore aggiornamento disponibilità per la corsa scelta!');
        });
  }

  onSubmitConsolidaTurno() {
    // stop here if form is invalid
    /* if (this.aggiungiBambinoForm.invalid) {
      return;
    } */

    let d: Disponibilita;
    d.username = this.trovaDisponibilitaForm.value.username;
    d.linea = this.consolidaTurnoForm.value.linea;
    d.data = this.consolidaTurnoForm.value.data;
    d.verso = this.consolidaTurnoForm.value.verso;

    this.userService.consolidaTurno(d)
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
}