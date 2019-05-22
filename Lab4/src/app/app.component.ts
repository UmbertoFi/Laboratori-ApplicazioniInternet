import {Component, OnInit} from '@angular/core';
import {PageEvent} from '@angular/material';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Lab4';
  /* linee = [
    {
      nome: '12',
      corse: [
        {
          data: '2019/04/30',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.30',
                persone: []
              },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '13.35',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                nome: 'Piazza Mellano',
                ora: '13.50',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              }]
            }
          ]
        },
        {
          data: '2019/05/30',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.30',
                persone: []
              },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '13.35',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Benedetta', selected: false},
                    {nome: 'Aurora', selected: false},
                    {nome: 'Chanel', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Simone', selected: false},
                    {nome: 'Claudia', selected: false}
                  ]
                }]
            }
          ]
        }
      ]
    },
    {
      nome: '58',
      corse: [
        {
          data: '2019/04/30',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.30',
                persone: []
              },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '13.35',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Benedetta', selected: false},
                    {nome: 'Aurora', selected: false},
                    {nome: 'Chanel', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Simone', selected: false},
                    {nome: 'Claudia', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/05/30',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Benedetta', selected: false},
                  {nome: 'Aurora', selected: false},
                  {nome: 'Chanel', selected: false},
                  {nome: 'Matteo', selected: false},
                  {nome: 'Sara', selected: false},
                  {nome: 'Simone', selected: false},
                  {nome: 'Claudia', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.00',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.30',
                persone: []
              },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '13.35',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Benedetta', selected: false},
                    {nome: 'Aurora', selected: false},
                    {nome: 'Chanel', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Simone', selected: false},
                    {nome: 'Claudia', selected: false}
                  ]
                }]
            }
          ]
        }
      ]
    }
  ]; */
  linea = [{
    nome: '12',
    data: '2019/04/30',
    verso: 'Andata',
    fermate: [{
      nome: 'Piazza Mellano',
      ora: '07.35',
      persone: [
        {nome: 'Benedetta', selected: false},
        {nome: 'Aurora', selected: false},
        {nome: 'Chanel', selected: false},
        {nome: 'Matteo', selected: false},
        {nome: 'Sara', selected: false},
        {nome: 'Simone', selected: false},
        {nome: 'Claudia', selected: false}
      ]
    },
      {
        nome: 'Via Primo Alpini',
        ora: '07.40',
        persone: [
          {nome: 'Giacomo', selected: false},
          {nome: 'Emma', selected: false}
        ]
      },
      {
        nome: 'Via Vigo',
        ora: '07.50',
        persone: [
          {nome: 'Isabel', selected: false},
          {nome: 'Mohammed', selected: false},
          {nome: 'Iaia', selected: false}
        ]
      },
      {
        nome: 'Piazza XXV Aprile',
        ora: '07.55',
        persone: [
          {nome: 'Shibo', selected: false},
          {nome: 'Vittoria', selected: false}
        ]
      },
      {
        nome: 'Scuola',
        ora: '08.00',
        persone: []
      }]
  },
    {
      nome: '12',
      data: '2019/05/22',
      verso: 'Andata',
      fermate: [{
        nome: 'Piazza Mellano',
        ora: '07.35',
        persone: [
          {nome: 'Benedetta', selected: false},
          {nome: 'Aurora', selected: false},
          {nome: 'Chanel', selected: false},
          {nome: 'Matteo', selected: false},
          {nome: 'Sara', selected: false},
          {nome: 'Simone', selected: false},
          {nome: 'Claudia', selected: false}
        ]
      },
        {
          nome: 'Via Primo Alpini',
          ora: '07.40',
          persone: [
            {nome: 'Giacomo', selected: false},
            {nome: 'Emma', selected: false}
          ]
        },
        {
          nome: 'Via Vigo',
          ora: '07.50',
          persone: [
            {nome: 'Isabel', selected: false},
            {nome: 'Mohammed', selected: false},
            {nome: 'Iaia', selected: false}
          ]
        },
        {
          nome: 'Piazza XXV Aprile',
          ora: '07.55',
          persone: [
            {nome: 'Shibo', selected: false},
            {nome: 'Vittoria', selected: false}
          ]
        },
        {
          nome: 'Scuola',
          ora: '08.00',
          persone: []
        }]
    },

    {
      nome: '12',
      data: '2019/05/19',
      verso: 'Andata',
      fermate: [{
        nome: 'Piazza Mellano',
        ora: '07.35',
        persone: [
          {nome: 'Benedetta', selected: false},
          {nome: 'Aurora', selected: false},
          {nome: 'Chanel', selected: false},
          {nome: 'Matteo', selected: false},
          {nome: 'Sara', selected: false},
          {nome: 'Simone', selected: false},
          {nome: 'Claudia', selected: false}
        ]
      },
        {
          nome: 'Via Primo Alpini',
          ora: '07.40',
          persone: [
            {nome: 'Giacomo', selected: false},
            {nome: 'Emma', selected: false}
          ]
        },
        {
          nome: 'Via Vigo',
          ora: '07.50',
          persone: [
            {nome: 'Isabel', selected: false},
            {nome: 'Mohammed', selected: false},
            {nome: 'Iaia', selected: false}
          ]
        },
        {
          nome: 'Piazza XXV Aprile',
          ora: '07.55',
          persone: [
            {nome: 'Shibo', selected: false},
            {nome: 'Vittoria', selected: false}
          ]
        },
        {
          nome: 'Scuola',
          ora: '08.00',
          persone: []
        }]
    },
    {
      nome: '12',
      data: '2019/05/23',
      verso: 'Andata',
      fermate: [{
        nome: 'Piazza Mellano',
        ora: '07.35',
        persone: [
          {nome: 'Benedetta', selected: false},
          {nome: 'Aurora', selected: false},
          {nome: 'Chanel', selected: false},
          {nome: 'Matteo', selected: false},
          {nome: 'Sara', selected: false},
          {nome: 'Simone', selected: false},
          {nome: 'Claudia', selected: false}
        ]
      },
        {
          nome: 'Via Primo Alpini',
          ora: '07.40',
          persone: [
            {nome: 'Giacomo', selected: false},
            {nome: 'Emma', selected: false}
          ]
        },
        {
          nome: 'Via Vigo',
          ora: '07.50',
          persone: [
            {nome: 'Isabel', selected: false},
            {nome: 'Mohammed', selected: false},
            {nome: 'Iaia', selected: false}
          ]
        },
        {
          nome: 'Piazza XXV Aprile',
          ora: '07.55',
          persone: [
            {nome: 'Shibo', selected: false},
            {nome: 'Vittoria', selected: false}
          ]
        },
        {
          nome: 'Scuola',
          ora: '08.00',
          persone: []
        }]
    },
    {
      nome: '12',
      data: '2019/05/27',
      verso: 'Andata',
      fermate: [{
        nome: 'Piazza Mellano',
        ora: '07.35',
        persone: [
          {nome: 'Benedetta', selected: false},
          {nome: 'Aurora', selected: false},
          {nome: 'Chanel', selected: false},
          {nome: 'Matteo', selected: false},
          {nome: 'Sara', selected: false},
          {nome: 'Simone', selected: false},
          {nome: 'Claudia', selected: false}
        ]
      },
        {
          nome: 'Via Primo Alpini',
          ora: '07.40',
          persone: [
            {nome: 'Giacomo', selected: false},
            {nome: 'Emma', selected: false}
          ]
        },
        {
          nome: 'Via Vigo',
          ora: '07.50',
          persone: [
            {nome: 'Isabel', selected: false},
            {nome: 'Mohammed', selected: false},
            {nome: 'Iaia', selected: false}
          ]
        },
        {
          nome: 'Piazza XXV Aprile',
          ora: '07.55',
          persone: [
            {nome: 'Shibo', selected: false},
            {nome: 'Vittoria', selected: false}
          ]
        },
        {
          nome: 'Scuola',
          ora: '08.00',
          persone: []
        }]
    },
    {
      nome: '12',
      data: '2018/05/28',
      verso: 'Andata',
      fermate: [{
        nome: 'Piazza Mellano',
        ora: '07.35',
        persone: [
          {nome: 'Benedetta', selected: false},
          {nome: 'Aurora', selected: false},
          {nome: 'Chanel', selected: false},
          {nome: 'Matteo', selected: false},
          {nome: 'Sara', selected: false},
          {nome: 'Simone', selected: false},
          {nome: 'Claudia', selected: false}
        ]
      },
        {
          nome: 'Via Primo Alpini',
          ora: '07.40',
          persone: [
            {nome: 'Giacomo', selected: false},
            {nome: 'Emma', selected: false}
          ]
        },
        {
          nome: 'Via Vigo',
          ora: '07.50',
          persone: [
            {nome: 'Isabel', selected: false},
            {nome: 'Mohammed', selected: false},
            {nome: 'Iaia', selected: false}
          ]
        },
        {
          nome: 'Piazza XXV Aprile',
          ora: '07.55',
          persone: [
            {nome: 'Shibo', selected: false},
            {nome: 'Vittoria', selected: false}
          ]
        },
        {
          nome: 'Scuola',
          ora: '08.00',
          persone: []
        }]
    }];
  pageEvent: PageEvent;
  length = this.linea.length;
  pageSize = 1;
  pageIndex = 0;

  ngOnInit(): void {
    this.linea.sort((a, b) => a.data.localeCompare(b.data));
  }

  clickPersona($event: MouseEvent, idFermata, idPersona, idLinea) {
    const date = new Date(this.linea[idLinea].data).getTime();
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();
    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();

    if (this.linea[idLinea].fermate[idFermata].persone[idPersona].selected === false && (date - curdate) === 0) {
      this.linea[idLinea].fermate[idFermata].persone[idPersona].selected = true;
    } else {
      this.linea[idLinea].fermate[idFermata].persone[idPersona].selected = false;
    }
  }

  selezionaPersona(idFermata: number, idPersona: number, idLinea: number) {
    if (this.linea[idLinea].fermate[idFermata].persone[idPersona].selected === true) {
      return 'personaSelezionata';
    }
  }

  personeOrdinateByNome(i: number, idLinea: number) {
    return this.linea[idLinea].fermate[i].persone.sort((a, b) => a.nome.localeCompare(b.nome));
  }

  fermateOrdinateByOra(idLinea: number) {
    return this.linea[idLinea].fermate.sort((a, b) => a.ora.localeCompare(b.ora));
  }

  a(pageEvent: PageEvent) {
    let i;
    let date;
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0'); // January is 0!
    const yyyy = today.getFullYear();

    const cur = yyyy + '/' + mm + '/' + dd;
    const curdate = new Date(cur).getTime();
    if (pageEvent === undefined) {
      for (i = 0; i < this.linea.length; i++) {
        date = new Date(this.linea[i].data).getTime();
        if (date - curdate >= 0) {
          break;
        }
      }
      this.pageIndex = i;
      return i;
    } else {
      return pageEvent.pageIndex;
    }
  }
}

