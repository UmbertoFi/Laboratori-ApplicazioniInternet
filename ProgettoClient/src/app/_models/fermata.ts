import {Persona} from './persona';

export class Fermata {

  constructor(
    public nome: string,
    public id_fermata: number,
    public ora: string,
    public persone: Persona[]) { }

}
