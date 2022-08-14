import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import { Sch } from 'src/app/model';
import {HttpService} from "../../http.service"
import {UserService} from "../../user.service"

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.css']
})
export class ScheduleComponent implements OnInit {
  
  id:number;
  exform: FormGroup;
  fun: string;
  error:boolean;
  errorMessage: string;

  constructor(private httpService: HttpService, public userService:UserService, private route:ActivatedRoute,private router: Router) { 
    this.id = 0;
    this.fun = "start";
    this.error = false;
   this.errorMessage = "";
    this.exform = new FormGroup({
      'date' : new FormControl(null, Validators.required),
      'time' : new FormControl(null, Validators.required)
    });
  }

  ngOnInit(): void {
    this.route.params.subscribe(p => {
      this.id = p['id'];
    });
  }
  schedule():void{
    let date: number[] = []
    let tem = this.exform.value.date.split("-")
    let tem1 = this.exform.value.time.split(":")
    date.push(tem[0], tem[1], tem[2], tem1[0], tem1[1])
    let sch = new Sch(this.id,date , this.fun)
    console.log("Date: " + this.exform.value.date)
    console.log("Time: " + this.exform.value.time)
    this.httpService.schMachine("http://localhost:8080/api/machine/schedule",sch,this.userService.getToken()).subscribe(
      (response) => {    
        this.router.navigateByUrl("machine");
        },(error) => {
          this.error = true;
          this.errorMessage = error.error.reason
          if(this.errorMessage === undefined)
            this.errorMessage = error.message;
        }
     
    )
  }

  get date(){
    return this.exform.get("date");
  }
  get time(){
    return this.exform.get("time");
  }
}
