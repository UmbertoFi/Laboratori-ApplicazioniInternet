import {Injectable} from '@angular/core';
import {Linea} from './linea';
import {LINE} from './dati-linea';
import {Corsa} from './corsa';

@Injectable()
export class LineaService {


  constructor() {
  }

  getLinee(): Linea[] {
    return LINE;
  }
/*
  getNomeLinee(): string[] {
   return this.getLinee().map(x => x.nome);
  }*/
}
