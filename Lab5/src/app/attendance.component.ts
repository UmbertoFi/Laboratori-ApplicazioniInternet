import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';
import {LineaService} from './linea.service';
import {Linea} from './linea';
import {AlertService, AuthenticationService, UserService} from './_services';
import {Prenotazione} from './_models';
import {Router} from '@angular/router';
import {Bambino} from './bambino';
import {nomeLinea} from './nomeLinea';
import {CorsaNew} from './corsaNew';
import {TrattaNew} from './trattaNew';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {
  title = 'Lab5';

  linee: Linea[];
  bambini: Bambino[];
  nomiLinee: nomeLinea[];
  corse: CorsaNew[];
  tratte: TrattaNew;


  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;

  constructor(private lineaService: LineaService, private userService: UserService, private authenticationService: AuthenticationService, private alertService: AlertService, private router: Router) {
  }

  ngOnInit(): void {
    let promiseBambini = fetch('http://localhost:8080/utility/children')
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.bambini = data;
      });

    let promiseLinea = fetch('http://localhost:8080/lines')
      .then((data) => {
        return data.json();
      }).then((data) => {
        this.nomiLinee = data;
        return;
      }).then(() => {
        let promiseCorsa = fetch('http://localhost:8080/utility/corse/' + this.nomiLinee[0].nome)
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
          .then(()=>{
            let promiseTratte = fetch('http://localhost:8080/utility/reservations/'+this.nomiLinee[0].nome+'/'+this.corse[this.pageIndex].data)
              .then((data)=>{
                return data.json();
              }).then((data)=>{
                this.tratte = data;
                this.tratte.fermateA.sort((a, b) => a.ora.localeCompare(b.ora));
                this.tratte.fermateR.sort((a, b) => a.ora.localeCompare(b.ora));
            })
          });
      });
  }

  clickPersona($event: MouseEvent, verso, idFermata, idPersona) {
    const date = new Date(this.corse[this.pageIndex].data).getTime();
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:max-line-length
    if(verso==0){
      if (this.tratte.fermateA[idFermata].persone[idPersona].selected === false /* && (date - curdate) === 0 */) {
        this.tratte.fermateA[idFermata].persone[idPersona].selected = true;
      } else {
        this.tratte.fermateA[idFermata].persone[idPersona].selected = false;
      }
      this.userService.updateprenotazione(this.tratte.fermateA[idFermata].persone[idPersona].id_bambino,this.tratte.fermateA[idFermata].id_fermata,this.corse[this.pageIndex].data,0).subscribe();
    } else{
      if (this.tratte.fermateR[idFermata].persone[idPersona].selected === false /* && (date - curdate) === 0 */) {
        this.tratte.fermateR[idFermata].persone[idPersona].selected = true;
      } else {
        this.tratte.fermateR[idFermata].persone[idPersona].selected = false;
      }
      this.userService.updateprenotazione(this.tratte.fermateR[idFermata].persone[idPersona].id_bambino,this.tratte.fermateR[idFermata].id_fermata,this.corse[this.pageIndex].data,1).subscribe();
    }
  }

  selezionaPersona(verso: number, idFermata: number, idPersona: number) {
    if(verso==0){
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
    if(verso==0){
      return this.tratte.fermateA[idFermata].persone.sort((a, b) => a.nome.localeCompare(b.nome));
    } else{
      return this.tratte.fermateR[idFermata].persone.sort((a, b) => a.nome.localeCompare(b.nome));
    }
  }

  fermateOrdinateByOra(verso: number) {
    if(verso==0){
      return this.tratte.fermateA.sort((a, b) => a.ora.localeCompare(b.ora));
    } else{
      return this.tratte.fermateR.sort((a, b) => a.ora.localeCompare(b.ora));
    }
  }

  selezionaLineaMenu(idLinea: number) {
    this.lineaSelezionataMenu = idLinea;
    let promiseCorsa = fetch('http://localhost:8080/utility/corse/' + this.nomiLinee[this.lineaSelezionataMenu].nome)
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
      .then(()=>{
        let promiseTratte = fetch('http://localhost:8080/utility/reservations/'+this.nomiLinee[this.lineaSelezionataMenu].nome+'/'+this.corse[this.pageIndex].data)
          .then((data)=>{
            return data.json();
          }).then((data)=>{
            this.tratte = data;
          })
      });
  }

  selezionaCorsaPaginator(pageEvent: PageEvent) {
    this.pageIndex = pageEvent.pageIndex;
    let promiseTratte = fetch('http://localhost:8080/utility/reservations/'+this.nomiLinee[this.lineaSelezionataMenu].nome+'/'+this.corse[this.pageIndex].data)
      .then((data)=>{
        return data.json();
      }).then((data)=>{
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
    this.userService.getbambini().subscribe(
      val => {
        this.bambini = val;
      }
    );
  }


  inserisciBambinoNonPrenotato($event: MouseEvent, id_bambino: number, linea: string, id_fermata: number, verso: number, data: string) {
    this.userService.inserisciPrenotazioneRitardata(id_bambino, linea, id_fermata, verso, data).subscribe();
    console.log('aaaaaaaaaa');
    return;
  }

  mostra($event: MouseEvent) {
    console.log(this.bambini);
    console.log(this.nomiLinee);
    console.log(this.corse);
    console.log(this.tratte);
  }
}

