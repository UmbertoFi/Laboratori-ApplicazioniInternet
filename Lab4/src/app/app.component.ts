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
        {nome : 'Benedetta'},
        {nome : 'Aurora'},
        {nome : 'Chanel'},
        {nome : 'Matteo'},
        {nome : 'Sara'},
        {nome : 'Simone'},
        {nome : 'Claudia'}
      ]},
      {
        nome: 'Via Primo Alpini',
        ora : '07.40',
        persone : [
          {nome : 'Giacomo'},
          {nome : 'Emma'}
        ]},
      {
        nome: 'Via Vigo',
        ora : '07.50',
        persone : [
          {nome : 'Isabel'},
          {nome : 'Mohammed'},
          {nome : 'Iaia'}
        ]},
      {
        nome: 'Piazza XXV Aprile',
        ora : '07.55',
        persone : [
          {nome : 'Shibo'},
          {nome : 'Vittoria'}
        ]},
      {
        nome: 'Scuola',
        ora : '08.00',
        persone : [
        ]}]
  };

  personaStile: boolean;

  constructor() {
    this.personaStile = false;
  }

  selezionaPersona($event: MouseEvent) {
    if (this.personaStile === false) {
      this.personaStile = true;
    } else {
      this.personaStile = false;
    }
  }
}
