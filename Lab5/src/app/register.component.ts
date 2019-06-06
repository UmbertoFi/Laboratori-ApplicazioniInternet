import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService, AlertService} from './_services';
import {Router} from '@angular/router';
import {first} from 'rxjs/operators';
import {LineaService} from './linea.service';
import {Linea} from './linea';
import {User} from './_models';


// import custom validator to validate that password and confirm password fields match


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  submitted = false;
  email_blur = false;
  password_blur = false;
  confirmPassword_blur = false;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private alertService: AlertService,
              private router: Router) {
  }

  ngOnInit() {
    if (localStorage.getItem('access_token') != null) {
      this.alertService.error('Utente già loggato! Premere Logout per effettuare altre operazioni!', true);
      this.router.navigate(['/attendance']);
    }
    this.registerForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(12)]],
      confirmPassword: ['', Validators.required]
    }, {
      validators: [MustMatch('password', 'confirmPassword')]
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }
    console.log('ci siamo');
    this.userService.register(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error);
        });
  }

  onChange() {
    notPresent(this.userService, this.registerForm);
  }

  onBlur(field: number) {
    if(field==0){
      this.email_blur = true;
    } else if(field==1){
      this.password_blur = true;
    } else if(field==2){
      this.confirmPassword_blur = true;
    }
    notPresent(this.userService, this.registerForm);
  }
}

//
// custom validator to check that two fields match
function MustMatch(controlName: string, matchingControlName: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlName];
    const matchingControl = formGroup.controls[matchingControlName];

    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      // return if another validator has already found an error on the matchingControl
      return;
    }

    // set error on matchingControl if validation fails
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({mustMatch: true});
    } else {
      matchingControl.setErrors(null);
    }
  };
}

function notPresent(userService: UserService, registerForm: FormGroup) {
  let checkUsernameResponse = userService.notPresent(registerForm.controls['email'].value)
    .subscribe(val => {
      if(val.available==false) {    // CAMBIARE LOGICA TRUE/FALSE
        registerForm.controls['email'].setErrors({notPresent: true})
      }
    });
}


