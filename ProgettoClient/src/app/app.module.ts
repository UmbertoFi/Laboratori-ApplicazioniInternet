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
  MatButtonModule, MatMenuModule, MatSelectModule, MatDatepickerModule, MatNativeDateModule
} from '@angular/material';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {LineaService} from './_services/linea.service';

import {LoginComponent} from './login.component';
import {RegisterComponent} from './register.component';
import {AttendanceComponent} from './attendance.component';
import {RouterModule, Routes} from '@angular/router';
import {UserService} from './_services';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AlertService} from './_services/alert.service';
import {AlertComponent} from './_directives';
import {AuthenticationService} from './_services/authentication.service';
import {JwtInterceptor} from './jwt.interceptor';
import {SimpleuserComponent} from './simpleuser.component';
import {AdminComponent} from './admin.component';
import {WebSocketService} from './_services/websocket.service';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'attendance', component: AttendanceComponent},
  {path: 'simpleuser', component: SimpleuserComponent},
  {path: 'admin', component: AdminComponent}
];


@NgModule({
  declarations: [
    AppComponent, LoginComponent, RegisterComponent, AttendanceComponent, AlertComponent, SimpleuserComponent, AdminComponent
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
    RouterModule.forRoot(routes, {enableTracing: true}),
    ReactiveFormsModule,
    HttpClientModule,
    MatSelectModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true}, LineaService, UserService, AlertService, AuthenticationService, MatDatepickerModule, WebSocketService],
  bootstrap: [AppComponent]
})
export class AppModule {
}


