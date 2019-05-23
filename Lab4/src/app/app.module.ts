import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatListModule,
  MatIconModule,
  MatCardModule,
  MatPaginatorModule,
  MatFormFieldModule,
  MatTabsModule,
  MatButtonModule, MatMenuModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {LineaService} from './linea.service';

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MatListModule,
    MatIconModule,
    MatCardModule,
    MatPaginatorModule,
    MatFormFieldModule,
    FormsModule,
    MatTabsModule,
    MatButtonModule,
    MatMenuModule
  ],
  providers: [LineaService],
  bootstrap: [AppComponent]
})
export class AppModule {
}


