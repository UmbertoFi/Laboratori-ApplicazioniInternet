import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';
import {LineaService} from './linea.service';
import {Linea} from './linea';
import {LINE} from './dati';
import {UserService} from './_services';
import {Prenotazione} from './_models';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {
  title = 'Lab4';

  linee: Linea[];

  pageEvent: PageEvent;
  length: number;
  pageSize = 1;
  pageIndex = 0;
  lineaSelezionataMenu = 0;

  constructor(private lineaService: LineaService, private userService: UserService) {
    this.getLinee();
  }

  ngOnInit(): void {
    this.length = this.linee[this.lineaSelezionataMenu].corse.length;
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.linee.length; i++) {
      this.linee[i].corse.sort((a, b) => a.data.localeCompare(b.data));
    }

    let j;
    let date;
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:prefer-for-of
    for (j = 0; j < this.linee[this.lineaSelezionataMenu].corse.length; j++) {
      date = new Date(this.linee[this.lineaSelezionataMenu].corse[j].data).getTime();
      if (date - curdate >= 0) {
        break;
      }
    }
    this.pageIndex = j;

  }

  getLinee(): void {
    // this.linee = LineaService.getLinee() e mettere static dall'altro lato

    this.linee = this.lineaService.getLinee();
    for(let l of this.linee) {
      for(let c of l.corse){
        for(let t of c.tratte){
          for(let f of t.fermate){
            for(let p of f.persone){
              let prenotazione = new Prenotazione(p.nome,f.nome,t.verso);
              this.userService.inserisciPrenotazione(prenotazione,l.nome,c.data).subscribe();
            }
          }
        }
      }
    }
  }

  clickPersona($event: MouseEvent, verso, idFermata, idPersona) {
    const date = new Date(this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].data).getTime();
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:max-line-length
    if (this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected === false && (date - curdate) === 0) {
      this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected = true;
    } else {
      this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected = false;
    }
  }

  selezionaPersona(verso: number, idFermata: number, idPersona: number) {
    if (this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone[idPersona].selected === true) {
      return 'personaSelezionata';
    }
  }

  personeOrdinateByNome(verso: number, idFermata: number) {
    // tslint:disable-next-line:max-line-length
    return this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate[idFermata].persone.sort((a, b) => a.nome.localeCompare(b.nome));
  }

  fermateOrdinateByOra(verso: number) {
    return this.linee[this.lineaSelezionataMenu].corse[this.pageIndex].tratte[verso].fermate.sort((a, b) => a.ora.localeCompare(b.ora));
  }

  selezionaLineaMenu(idLinea: number) {
    this.lineaSelezionataMenu = idLinea;
    this.length = this.linee[idLinea].corse.length;
    let j;
    let date;
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    // tslint:disable-next-line:prefer-for-of
    for (j = 0; j < this.linee[this.lineaSelezionataMenu].corse.length; j++) {
      date = new Date(this.linee[this.lineaSelezionataMenu].corse[j].data).getTime();
      if (date - curdate >= 0) {
        break;
      }
    }
    this.pageIndex = j;
  }

  selezionaCorsaPaginator(pageEvent: PageEvent) {
    this.pageIndex = pageEvent.pageIndex;
    return this.pageEvent;
  }
}

