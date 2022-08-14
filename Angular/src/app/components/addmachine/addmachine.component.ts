import { Component, OnInit } from '@angular/core';
import {HttpService} from "../../http.service"
import {UserService} from "../../user.service"
import { Router, ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import { MachineAdd } from 'src/app/model';



@Component({
  selector: 'app-addmachine',
  templateUrl: './addmachine.component.html',
  styleUrls: ['./addmachine.component.css']
})
export class AddmachineComponent implements OnInit {
  error:boolean;
  errorMessage: string;
  exform: FormGroup;

  constructor(private httpService: HttpService, public userService:UserService,private router: Router) { 
    this.error = false;
    this.errorMessage = "";
    this.exform = new FormGroup({
      'name' : new FormControl(null, Validators.required),
    });
  }

  ngOnInit(): void {
  }
  create():void{
    let machine = new MachineAdd(this.exform.value.name)
    this.httpService.createMachine("http://localhost:8080/api/machine/create",machine,this.userService.getToken()).subscribe(
      (response) => {    this.error = false;
                        this.router.navigateByUrl("machine");

                        },(error) => {
                        this.error = true;
                        this.errorMessage = error.error.reason
                        if(this.errorMessage === undefined)
                          this.errorMessage = error.message;
                        }
    )
  }
  get name() {
    return this.exform.get('name');
  }
}
