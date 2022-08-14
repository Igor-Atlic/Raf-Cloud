import { Component, OnInit , OnDestroy} from '@angular/core';
import {HttpService} from "../../http.service"
import {UserService} from "../../user.service"
import {User , Machine, Search} from "../../model";
import {Subject,NEVER, Observable, timer} from 'rxjs';
import { FormGroup, FormControl, Validators} from '@angular/forms';
import {filter, retry, switchMap, takeUntil } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-machine',
  templateUrl: './machine.component.html',
  styleUrls: ['./machine.component.css']
})
export class MachineComponent implements OnInit {

  machine: Machine[] = []
  error:boolean;
  errorMessage: string;
  running:boolean;
  stopped:boolean;
  schedule:boolean;
  exform: FormGroup;
  s= new Subject();

  constructor(private httpService: HttpService, public userService:UserService,private router: Router) {
    this.error = false;
    this.errorMessage = "";
    this.running = false;
    this.stopped = false;
    this.schedule = true;
    this.exform = new FormGroup({
      'name' : new FormControl(null),
      'dateFrom' : new FormControl(null),
      'dateTo' : new FormControl(null)
    });


  }

  ngOnInit(): void {
    this.httpService.getMachine("http://localhost:8080/api/machine/all",this.userService.getToken()).subscribe((m)=>{
       this.machine = m;
      })

     if(this.userService.getPermissions().includes(this.userService.getRestart()) ||
     this.userService.getPermissions().includes(this.userService.getStop()) ||
     this.userService.getPermissions().includes(this.userService.getStart())){
        this.schedule = false
     }

     timer(10000, 5000).pipe(
      takeUntil(this.s),
      switchMap(() => this.httpService.getMachine("http://localhost:8080/api/machine/all" ,this.userService.getToken()))).subscribe(
        res =>{
              res.forEach(element => {
                this.machine.forEach(m => {
                  if (m.machineId === element.machineId){
                    m.status = element.status;
                  }
                });
              });
        },
        er =>{ console.log(er)
          this.error = true;
          this.errorMessage = er.error.reason
          if(this.errorMessage === undefined)
            this.errorMessage = er.message;
        }
      )
  }
  ngOnDestroy():void{
      this.s.next()
  }
  delete(id:number):void{
    this.httpService.deleteUser("http://localhost:8080/api/machine/delete/" + id,this.userService.getToken()).subscribe(

     (response) => {    this.error = false;
      this.machine.forEach((machine,index) =>{
        if(machine.machineId === id){
          this.machine.splice(index, 1);
        }
     });
     },(error) => {
      this.error = true;
      this.errorMessage = error.error.reason
      if(this.errorMessage === undefined)
        this.errorMessage = error.message;
     }

    )
  }
  sch(id:number){
    this.router.navigateByUrl("schedule/" + id)
  }
  search():void{
    let status = []
    let dateFrom = "2000-01-01"
    let dateTo = "2050-01-01"
    let name = "";
    if (this.running === true){
      status.push("RUNNING")
    }
    if (this.stopped === true){
      status.push("STOPPED")
    }
    if (this.running === false && this.stopped === false){
      status.push("RUNNING")
      status.push("STOPPED")
    }
    if(this.exform.value.name != null){
      name = this.exform.value.name
    }
    if(this.exform.value.dateFrom === null || this.exform.value.dateFrom === ""){
      dateFrom = "2000-01-01"
    }else{dateFrom = this.exform.value.dateFrom }

    if(this.exform.value.dateTo === null || this.exform.value.dateTo === ""){
      dateTo = "2050-01-01"
    }else{dateTo = this.exform.value.dateTo }
    let search = new Search(name,status,dateFrom,dateTo)
    console.log(this.exform.value)
    this.httpService.searchMachine("http://localhost:8080/api/machine/search", search, this.userService.getToken()).subscribe(
      (response) => {    this.error = false;
       this.machine = response
      },(error) => {
       this.error = true;
       this.errorMessage = error.error.reason
       if(this.errorMessage === undefined)
         this.errorMessage = error.message;
      }

     )
  }
  start(id:number):void{
    this.httpService.getStart("http://localhost:8080/api/machine/start/" + id,this.userService.getToken()).subscribe(
      (response) => {    this.error = false;
        this.machine.forEach((machine) =>{
          let subject = new Subject();
          let i = 0;
          if(machine.machineId === id){
            timer(9000, 500).pipe(
              takeUntil(subject),
              switchMap(() => this.httpService.getMachineId("http://localhost:8080/api/machine/" + id,this.userService.getToken()))).subscribe(
                res =>{ console.log(res.status)
                        i = i+1
                        if( i > 20){
                          subject.next();
                        }
                        if(res.status === "RUNNING"){
                          subject.next();
                            machine.status = "RUNNING"
                        }
                },
                er =>{ console.log(er)
                  i = i+1
                  if( i > 20){
                    subject.next();
                  }
                }
              )

          }
        })
        },(error) => {
         this.error = true;
         this.errorMessage = error.error.reason
         if(this.errorMessage === undefined)
           this.errorMessage = error.message;
        }

    )
  }
  stop(id:number):void{
    this.httpService.getStart("http://localhost:8080/api/machine/stop/" + id,this.userService.getToken()).subscribe(
      (response) => {    this.error = false;
        this.machine.forEach((machine) =>{
          let subject = new Subject();
          let i = 0;
          if(machine.machineId === id){
            timer(9000, 500).pipe(
              takeUntil(subject),
              switchMap(() => this.httpService.getMachineId("http://localhost:8080/api/machine/" + id,this.userService.getToken()))).subscribe(
                res =>{ console.log(res.status)
                        i = i+1
                        if( i > 20){
                          subject.next();
                        }
                        if(res.status === "STOPPED"){
                          subject.next();
                            machine.status = "STOPPED"
                        }
                },
                er =>{ console.log(er)
                  i = i+1
                  if( i > 20){
                    subject.next();
                  }
                }
              )

          }
        })
      },(error) => {
       this.error = true;
       this.errorMessage = error.error.reason
       if(this.errorMessage === undefined)
         this.errorMessage = error.message;
      })
  }
  restart(id:number):void{
    this.httpService.getStart("http://localhost:8080/api/machine/restart/" + id,this.userService.getToken()).subscribe(
      (response) => {    this.error = false;
        this.machine.forEach((machine) =>{
          let subject = new Subject();
          let i = 0;
          if(machine.machineId === id){
            timer(4000, 500).pipe(
              takeUntil(subject),
              switchMap(() => this.httpService.getMachineId("http://localhost:8080/api/machine/" + id,this.userService.getToken()))).subscribe(
                res =>{ console.log(res.status)
                        i = i+1
                        if( i > 20){
                          subject.next();
                        }
                        if(res.status === "STOPPED"){
                          subject.next();
                            machine.status = "STOPPED"
                        }
                },
                er =>{ console.log(er)
                  i = i+1
                  if( i > 20){
                    subject.next();
                  }
                }
              )

          }
        })
        this.machine.forEach((machine) =>{
          let subject = new Subject();
          let i = 0;
          if(machine.machineId === id){
            timer(9000, 500).pipe(
              takeUntil(subject),
              switchMap(() => this.httpService.getMachineId("http://localhost:8080/api/machine/" + id,this.userService.getToken()))).subscribe(
                res =>{ console.log(res.status)
                        i = i+1
                        if( i > 20){
                          subject.next();
                        }
                        if(res.status === "RUNNING"){
                          subject.next();
                            machine.status = "RUNNING"
                        }
                },
                er =>{ console.log(er)
                  i = i+1
                  if( i > 20){
                    subject.next();
                  }
                }
              )

          }
        })
      },(error) => {
       this.error = true;
       this.errorMessage = error.error.reason
       if(this.errorMessage === undefined)
         this.errorMessage = error.message;
      })
  }

  get name() {
    return this.exform.get('name');
  }
  get dateFrom() {
    return this.exform.get('dateFrom');
  }
  get dateTo() {
    return this.exform.get('dateTo');
  }

}
