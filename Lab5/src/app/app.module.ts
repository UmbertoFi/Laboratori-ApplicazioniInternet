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

import {LoginComponent} from './login.component';
import {RegisterComponent} from './register.component';
import {AttendanceComponent} from './attendance.component';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'attendance', component: AttendanceComponent}
];


@NgModule({
  declarations: [
    AppComponent, LoginComponent, RegisterComponent, AttendanceComponent
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
    MatMenuModule,
    RouterModule.forRoot(routes, {enableTracing: true})
  ],
  providers: [LineaService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
