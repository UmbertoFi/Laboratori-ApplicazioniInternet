import {Linea} from './linea';

export const LINE: Linea[] = [
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
                  {nome: 'Rebecca', selected: false},
                  {nome: 'Mario', selected: false},
                  {nome: 'Salvatore', selected: false},
                  {nome: 'Filippo', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giuseppe', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Enzo', selected: false},
                    {nome: 'Maria', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Angelo', selected: false}
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
                    {nome: 'Angelo', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Enzo', selected: false},
                    {nome: 'Maria', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giuseppe', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Rebecca', selected: false},
                    {nome: 'Mario', selected: false},
                    {nome: 'Salvatore', selected: false},
                    {nome: 'Filippo', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/12/05',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Mellano',
                ora: '07.35',
                persone: [
                  {nome: 'Patrizia', selected: false},
                  {nome: 'Romano', selected: false},
                  {nome: 'Marina', selected: false},
                  {nome: 'Natalia', selected: false}
                ]
              },
                {
                  nome: 'Via Primo Alpini',
                  ora: '07.40',
                  persone: [
                    {nome: 'Giulia', selected: false},
                    {nome: 'Francesca', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '07.50',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Irene', selected: false},
                    {nome: 'Matteo', selected: false}
                  ]
                },
                {
                  nome: 'Piazza XXV Aprile',
                  ora: '07.55',
                  persone: [
                    {nome: 'Federico', selected: false}
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
                    {nome: 'Federico', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigo',
                  ora: '13.40',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Irene', selected: false},
                    {nome: 'Matteo', selected: false}
                  ]
                },
                {
                  nome: 'Via Primo Alpini',
                  ora: '13.45',
                  persone: [
                    {nome: 'Giulia', selected: false},
                    {nome: 'Francesca', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Mellano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Patrizia', selected: false},
                    {nome: 'Romano', selected: false},
                    {nome: 'Massimo', selected: false},
                    {nome: 'Natalia', selected: false}
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
          data: '2019/05/23',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Pitagora',
                ora: '07.40',
                persone: [
                  {nome: 'Maurizio', selected: false},
                  {nome: 'Mimmo', selected: false},
                  {nome: 'Laura', selected: false}
                ]
              },
                {
                  nome: 'Piazza Castello',
                  ora: '07.40',
                  persone: [
                    {nome: 'Carlo', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Zaira', selected: false},
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '07.45',
                  persone: [
                    {nome: 'Teresa', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigone',
                  ora: '07.55',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false},
                    {nome: 'Tito', selected: false},
                    {nome: 'Tiberio', selected: false},
                    {nome: 'Carlo', selected: false}
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
                ora: '13.40',
                persone: []
              },
                {
                  nome: 'Via Vigone',
                  ora: '13.45',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false},
                    {nome: 'Tito', selected: false},
                    {nome: 'Tiberio', selected: false},
                    {nome: 'Carlo', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Teresa', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Castello',
                  ora: '13.55',
                  persone: [
                    {nome: 'Carlo', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Zaira', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Pitagora',
                  ora: '14.00',
                  persone: [
                    {nome: 'Maurizio', selected: false},
                    {nome: 'Mimmo', selected: false},
                    {nome: 'Laura', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2020/03/23',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Pitagora',
                ora: '07.40',
                persone: [
                  {nome: 'Sara', selected: false},
                  {nome: 'Ginevra', selected: false},
                  {nome: 'Renzo', selected: false}
                ]
              },
                {
                  nome: 'Piazza Castello',
                  ora: '07.40',
                  persone: [
                    {nome: 'Luca', selected: false},
                    {nome: 'Stefano', selected: false},
                    {nome: 'Angelica', selected: false},
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '07.45',
                  persone: [
                    {nome: 'Marco', selected: false},
                    {nome: 'Sergio', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigone',
                  ora: '07.55',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Christian', selected: false},
                    {nome: 'Tommaso', selected: false},
                    {nome: 'Andrea', selected: false},
                    {nome: 'Rachele', selected: false}
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
                ora: '13.40',
                persone: []
              },
                {
                  nome: 'Via Vigone',
                  ora: '13.45',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Christian', selected: false},
                    {nome: 'Tommaso', selected: false},
                    {nome: 'Andrea', selected: false},
                    {nome: 'Rachele', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Marco', selected: false},
                    {nome: 'Sergio', selected: false},
                    {nome: 'Cristina', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Castello',
                  ora: '13.55',
                  persone: [
                    {nome: 'Luca', selected: false},
                    {nome: 'Stefano', selected: false},
                    {nome: 'Angelica', selected: false},
                  ]
                },
                {
                  nome: 'Piazza Pitagora',
                  ora: '14.00',
                  persone: [
                    {nome: 'Sara', selected: false},
                    {nome: 'Ginevra', selected: false},
                    {nome: 'Renzo', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/07/20',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Piazza Pitagora',
                ora: '07.40',
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
                  nome: 'Piazza Castello',
                  ora: '07.45',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '07.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Via Vigone',
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
                ora: '13.40',
                persone: []
              },
                {
                  nome: 'Via Vigone',
                  ora: '13.45',
                  persone: [
                    {nome: 'Shibo', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Via Milano',
                  ora: '13.50',
                  persone: [
                    {nome: 'Isabel', selected: false},
                    {nome: 'Mohammed', selected: false},
                    {nome: 'Iaia', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Castello',
                  ora: '13.55',
                  persone: [
                    {nome: 'Giacomo', selected: false},
                    {nome: 'Emma', selected: false}
                  ]
                },
                {
                  nome: 'Piazza Pitagora',
                  ora: '14.00',
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
      nome: '33',
      corse: [
        {
          data: '2019/05/27',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Porta Nuova',
                ora: '08.05',
                persone: [
                  {nome: 'Antonino', selected: false},
                  {nome: 'Paolo', selected: false},
                  {nome: 'Umberto', selected: false}
                ]
              },
                {
                  nome: 'Vittorio EmanueleII',
                  ora: '08.10',
                  persone: [
                    {nome: 'Christian', selected: false},
                    {nome: 'Luca', selected: false},
                    {nome: 'Andrea', selected: false},
                    {nome: 'Lucia', selected: false},
                    {nome: 'Valentina', selected: false},
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '08.15',
                  persone: [
                    {nome: 'Francesco', selected: false},
                    {nome: 'Salvatore', selected: false},
                    {nome: 'Maria', selected: false},
                    {nome: 'Marianna', selected: false}
                  ]
                },
                {
                  nome: 'Stati Uniti',
                  ora: '08.20',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Davide', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Gino', selected: false},
                    {nome: 'Carla', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.35',
                persone: []
              },
                {
                  nome: 'Stati Uniti',
                  ora: '13.40',
                  persone: [
                    {nome: 'Filippo', selected: false},
                    {nome: 'Tommaso', selected: false},
                    {nome: 'Lorenzo', selected: false},
                    {nome: 'Tina', selected: false},
                    {nome: 'Rebecca', selected: false}
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '13.45',
                  persone: [
                    {nome: 'Dario', selected: false},
                    {nome: 'Emanuele', selected: false},
                    {nome: 'Valentina', selected: false}
                  ]
                },
                {
                  nome: 'Vittorio Emanuele II',
                  ora: '13.50',
                  persone: [
                    {nome: 'Diana', selected: false},
                    {nome: 'Sonia', selected: false},
                    {nome: 'Veronica', selected: false}
                  ]
                },
                {
                  nome: 'Porta Nuova',
                  ora: '13.55',
                  persone: [
                    {nome: 'Maura', selected: false},
                    {nome: 'Federico', selected: false},
                    {nome: 'Giorgio', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2020/01/16',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Porta Nuova',
                ora: '08.05',
                persone: [
                  {nome: 'Dario', selected: false},
                  {nome: 'Alberto', selected: false},
                  {nome: 'Stefania', selected: false}
                ]
              },
                {
                  nome: 'Vittorio EmanueleII',
                  ora: '08.10',
                  persone: [
                    {nome: 'Davide', selected: false},
                    {nome: 'Marco', selected: false},
                    {nome: 'Aida', selected: false},
                    {nome: 'Luciano', selected: false},
                    {nome: 'Valerio', selected: false},
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '08.15',
                  persone: [
                    {nome: 'Valentina', selected: false},
                    {nome: 'Sabrina', selected: false},
                    {nome: 'Ivn', selected: false},
                    {nome: 'Marilena', selected: false}
                  ]
                },
                {
                  nome: 'Stati Uniti',
                  ora: '08.20',
                  persone: [
                    {nome: 'Fabio', selected: false},
                    {nome: 'Giovanni', selected: false},
                    {nome: 'Piero', selected: false},
                    {nome: 'Gino', selected: false},
                    {nome: 'Carla', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.35',
                persone: []
              },
                {
                  nome: 'Stati Uniti',
                  ora: '13.40',
                  persone: [
                    {nome: 'Filippo', selected: false},
                    {nome: 'Tommaso', selected: false},
                    {nome: 'Loredana', selected: false},
                    {nome: 'Alessia', selected: false},
                    {nome: 'Ilaria', selected: false}
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '13.45',
                  persone: [
                    {nome: 'Sara', selected: false},
                    {nome: 'Emanuele', selected: false},
                    {nome: 'Cecilia', selected: false}
                  ]
                },
                {
                  nome: 'Vittorio Emanuele II',
                  ora: '13.50',
                  persone: [
                    {nome: 'Chiara', selected: false},
                    {nome: 'Nicole', selected: false},
                    {nome: 'Veronica', selected: false}
                  ]
                },
                {
                  nome: 'Porta Nuova',
                  ora: '13.55',
                  persone: [
                    {nome: 'Yuri', selected: false},
                    {nome: 'Federico', selected: false},
                    {nome: 'Giorgio', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/09/29',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Porta Nuova',
                ora: '08.05',
                persone: [
                  {nome: 'Silvia', selected: false},
                  {nome: 'Paolo', selected: false},
                  {nome: 'Ilaria', selected: false}
                ]
              },
                {
                  nome: 'Vittorio Emanuele II',
                  ora: '08.10',
                  persone: [
                    {nome: 'Alessandro', selected: false},
                    {nome: 'Luca', selected: false},
                    {nome: 'Alessio', selected: false},
                    {nome: 'Laura', selected: false},
                    {nome: 'Valentina', selected: false},
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '08.15',
                  persone: [
                    {nome: 'Francesca', selected: false},
                    {nome: 'Davide', selected: false},
                    {nome: 'Daniele', selected: false},
                    {nome: 'Marianna', selected: false}
                  ]
                },
                {
                  nome: 'Stati Uniti',
                  ora: '08.20',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Davide', selected: false},
                    {nome: 'Mattia', selected: false},
                    {nome: 'Ginevra', selected: false},
                    {nome: 'Gennaro', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.35',
                persone: []
              },
                {
                  nome: 'Stati Uniti',
                  ora: '13.40',
                  persone: [
                    {nome: 'Filippo', selected: false},
                    {nome: 'Giorgio', selected: false},
                    {nome: 'Lorenza', selected: false},
                    {nome: 'Angelica', selected: false},
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '13.45',
                  persone: [
                    {nome: 'Martina', selected: false},
                    {nome: 'Lidia', selected: false},
                    {nome: 'Valentina', selected: false}
                  ]
                },
                {
                  nome: 'Vittorio Emanuele II',
                  ora: '13.50',
                  persone: [
                    {nome: 'Debora', selected: false},
                    {nome: 'Sara', selected: false},
                    {nome: 'Camilla', selected: false}
                  ]
                },
                {
                  nome: 'Porta Nuova',
                  ora: '13.55',
                  persone: [
                    {nome: 'Alessio', selected: false},
                    {nome: 'Francesco', selected: false},
                    {nome: 'Giovanni', selected: false}
                  ]
                }]
            }
          ]
        },
        {
          data: '2019/11/31',
          tratte: [
            {
              verso: 'andata',
              fermate: [{
                nome: 'Porta Nuova',
                ora: '08.05',
                persone: [
                  {nome: 'Stefano', selected: false},
                  {nome: 'Luigi', selected: false},
                  {nome: 'Umberto', selected: false},
                  {nome: 'Luciano', selected: false}
                ]
              },
                {
                  nome: 'Vittorio Emanuele II',
                  ora: '08.10',
                  persone: [
                    {nome: 'Leonardo', selected: false},
                    {nome: 'Laura', selected: false},
                    {nome: 'Alberto', selected: false},
                    {nome: 'Lara', selected: false},
                    {nome: 'Valerio', selected: false},
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '08.15',
                  persone: [
                    {nome: 'Manuela', selected: false},
                    {nome: 'Marco', selected: false},
                    {nome: 'Sabrina', selected: false},
                    {nome: 'Jacopo', selected: false}
                  ]
                },
                {
                  nome: 'Stati Uniti',
                  ora: '08.20',
                  persone: [
                    {nome: 'Kevin', selected: false},
                    {nome: 'Daniel', selected: false},
                    {nome: 'Ilaria', selected: false},
                    {nome: 'Cecilia', selected: false},
                    {nome: 'Carla', selected: false}
                  ]
                },
                {
                  nome: 'Scuola',
                  ora: '08.30',
                  persone: []
                }]
            },
            {
              verso: 'ritorno',
              fermate: [{
                nome: 'Scuola',
                ora: '13.35',
                persone: []
              },
                {
                  nome: 'Stati Uniti',
                  ora: '13.40',
                  persone: [
                    {nome: 'Angelo', selected: false},
                    {nome: 'Marina', selected: false},
                    {nome: 'Valentino', selected: false},
                    {nome: 'Antonio', selected: false}
                  ]
                },
                {
                  nome: 'Re Umberto',
                  ora: '13.45',
                  persone: [
                    {nome: 'Davide', selected: false},
                    {nome: 'Elena', selected: false},
                    {nome: 'Vittoria', selected: false}
                  ]
                },
                {
                  nome: 'Vittorio Emanuele II',
                  ora: '13.50',
                  persone: [
                    {nome: 'Diana', selected: false},
                    {nome: 'Sonia', selected: false},
                    {nome: 'Veronica', selected: false},
                    {nome: 'Andrea', selected: false},
                    {nome: 'Matteo', selected: false},
                    {nome: 'Riccardo', selected: false}
                  ]
                },
                {
                  nome: 'Porta Nuova',
                  ora: '13.55',
                  persone: [
                    {nome: 'Anna', selected: false},
                    {nome: 'Nicola', selected: false},
                    {nome: 'Gino', selected: false}
                  ]
                }]
            }
          ]
        }
      ]
    }
];
