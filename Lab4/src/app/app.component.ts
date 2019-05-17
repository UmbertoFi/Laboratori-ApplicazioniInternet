import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Lab4';
  linea = {
    nome : '12',
    data : ' mar 13/03/2019',
    verso : 'Andata',
    fermate : [{
      nome: 'Piazza Mellano',
      ora : '07.35',
      persone : [
        {nome : 'Benedetta', selected: false},
        {nome : 'Aurora', selected: false},
        {nome : 'Chanel', selected: false},
        {nome : 'Matteo', selected: false},
        {nome : 'Sara', selected: false},
        {nome : 'Simone', selected: false},
        {nome : 'Claudia', selected: false}
      ]},
      {
        nome: 'Via Primo Alpini',
        ora : '07.40',
        persone : [
          {nome : 'Giacomo', selected: false},
          {nome : 'Emma', selected: false}
        ]},
      {
        nome: 'Via Vigo',
        ora : '07.50',
        persone : [
          {nome : 'Isabel', selected: false},
          {nome : 'Mohammed', selected: false},
          {nome : 'Iaia', selected: false}
        ]},
      {
        nome: 'Piazza XXV Aprile',
        ora : '07.55',
        persone : [
          {nome : 'Shibo', selected: false},
          {nome : 'Vittoria', selected: false}
        ]},
      {
        nome: 'Scuola',
        ora : '08.00',
        persone : [
        ]}]
  };

  clickPersona($event: MouseEvent, idFermata, idPersona) {
    if (this.linea.fermate[idFermata].persone[idPersona].selected === false) {
      this.linea.fermate[idFermata].persone[idPersona].selected = true;
    } else {
      this.linea.fermate[idFermata].persone[idPersona].selected = false;
    }
  }

  selezionaPersona(idFermata: number, idPersona: number) {
    if (this.linea.fermate[idFermata].persone[idPersona].selected === true) {
      return 'personaSelezionata';
    }
  }
}
