import {Injectable} from '@angular/core';
import {Linea} from './linea';
import {LINE} from './dati';

@Injectable()
export class LineaService {


  constructor() {
  }

  static getLinee(): Linea[] {
    return LINE;
  }
/*
  getNomeLinee(): string[] {
   return this.getLinee().map(x => x.nome);
  }*/
}
