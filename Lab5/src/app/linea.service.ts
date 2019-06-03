import {Injectable} from '@angular/core';
import {Linea} from './linea';
import {LINE} from './dati';
import {UserService} from './_services';

@Injectable()
export class LineaService {

  constructor(private userService : UserService) {
  }

  static getLinee(): Linea[] {
    // Popolare il database
    for(let l of LINE) {
      for(let c of l.corse){
        for(let t of c.tratte){
          for(let f of t.fermate){
            for(let p of f.persone){
              UserService.ins
            }
          }
        }
      }
    }
    // riempire l'oggetto
    return LINE;
  }
/*
  getNomeLinee(): string[] {
   return this.getLinee().map(x => x.nome);
  }*/
}
