import { Component, OnInit } from '@angular/core';
import {HttpService} from "../../http.service"
import {UserService} from "../../user.service"
import {User} from "../../model";


@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users: User[] = [];

  constructor(private httpService: HttpService, public userService:UserService) { }

  ngOnInit(): void {
    this.httpService.getUsers("http://localhost:8080/api/user/all",this.userService.getToken()).subscribe((u)=>{
       this.users = u;
      })
  }
  delete(id:number):void{
    this.httpService.deleteUser("http://localhost:8080/api/user/delete/" + id,this.userService.getToken()).subscribe((u)=>{
           this.users.forEach((user,index) =>{
              if(user.userId === id){
                this.users.splice(index, 1);
              }
           });
          })
  }
}
