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
import {Ruolo} from './_models';

@Component({
  selector: 'app-simpleuser',
  templateUrl: './simpleuser.component.html',
  styleUrls: ['./simpleuser.component.css']
})
export class SimpleuserComponent implements OnInit {
  title = 'Pedibus';

  nomiLinee: nomeLinea[];
  corse: CorsaNew[];
  tratte: TrattaNew;
  figli: Bambino[];
  aggiungiBambinoForm: FormGroup;
  submitted: boolean = false;
  candidatiAccompagnatoreForm: FormGroup;
  data: string;
  openDateForm: boolean = false;
  ruoli: Ruolo[];
  checkAdmin: boolean = false;
  checkAccompagnatore: boolean = false;
  checkSystemAdmin: boolean = false;


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

    this.candidatiAccompagnatoreForm = this.formBuilder.group({
      linea: ['', Validators.required],
      data: ['', Validators.required],
      verso: ['', Validators.required]
    });

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
        for(let r of this.ruoli){
          console.log(r);
          if(r.ruolo.localeCompare("admin")==0){
            this.checkAdmin = true;
          } else if(r.ruolo.localeCompare("system-admin")==0){
            this.checkSystemAdmin = true;
          } else if(r.ruolo.localeCompare("accompagnatore")==0){
            this.checkAccompagnatore = true;
          }
        }
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

  onSubmitAccompagnatore() {

    // stop here if form is invalid
    /* if (this.aggiungiBambinoForm.invalid) {
      return;
    } */

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
    if(this.openDateForm==false){
      this.openDateForm=true;
    }
    else{
      this.openDateForm=false;
    }
  }


  selezionaCorsaCalendario() {
    const pageDate = new Date(this.corse[this.pageIndex].data).setUTCHours(0o0,0o0,0o0,0o0);
    const date = new Date(this.data).setUTCHours(0o0,0o0,0o0,0o0);
    const diff = (date-pageDate)/(24*3600*1000);

    if((this.pageIndex+diff)<0 || (this.pageIndex+diff)>364){
      this.alertService.error('Corsa non esistente!');
      return;
    } else{
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
      this.openDateForm=false;
      return;
    }
  }

  cambiaSezione(url: string) {
    this.router.navigate(['/'+url]);
  }
}
