import {Component} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  title = 'Register';
  registerForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(15)]),
    password2: new FormControl('', [Validators.required, Validators.minLength(5), Validators.maxLength(15)])
  });
  submitted = false;

  // Ritorna i fields del form
  get f() {
    return this.registerForm.controls;
  }

  registraUtente() {
    this.submitted = true;
  }
}

