import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, ParamMap } from '@angular/router';
import {HttpService} from "../../http.service"
import {UserService} from "../../user.service"
import {User,UserC} from "../../model";



@Component({
  selector: 'app-edituser',
  templateUrl: './edituser.component.html',
  styleUrls: ['./edituser.component.css']
})
export class EdituserComponent implements OnInit {

  user: User;
  id:number;
  error:boolean;
  errorMessage: string;
  canC: boolean;
  canR:boolean;
  canU:boolean;
  canD:boolean;
  canSM:boolean;
  canS:boolean;
  canST:boolean;
  canRM:boolean;
  canCM:boolean;
  canDM:boolean;
  constructor(private httpService: HttpService, private userService:UserService, private route:ActivatedRoute,private router: Router) {
    this.user = new User(0,"","","",[]);
    this.id = 0;
    this.canC =false;
    this.canR =false;
    this.canU =false;
    this.canD =false;
    this.canSM =false;
    this.canS =false;
    this.canST =false;
    this.canRM =false;
    this.canCM =false;
    this.canDM =false;
    this.error = false;
   this.errorMessage = "";
  }

  ngOnInit(): void {
    this.route.params.subscribe(p => {
        this.id = p['id'];
      });

    this.httpService.getUser("http://localhost:8080/api/user/" + this.id, this.userService.getToken()).subscribe(
      (response) => {
            this.user = response;
           if(this.user.permissions.includes("can_create_users")){
              this.canC = true;
           }
           if(this.user.permissions.includes("can_read_users")){
                         this.canR = true;
                      }
           if(this.user.permissions.includes("can_update_users")){
                         this.canU = true;
                      }
            if(this.user.permissions.includes("can_delete_users")){
                          this.canD = true;
                       }
            if(this.user.permissions.includes("can_search_machines")){
              this.canSM = true;
            }
            if(this.user.permissions.includes("can_start_machines")){
                          this.canS = true;
                      }
            if(this.user.permissions.includes("can_stop_machines")){
                          this.canST = true;
                      }
            if(this.user.permissions.includes("can_restart_machines")){
                          this.canRM = true;
                        }
            if(this.user.permissions.includes("can_create_machines")){
              this.canCM = true;
                      }
            if(this.user.permissions.includes("can_destroy_machines")){
                          this.canDM = true;
                        }              
          },(error) => {
            this.error = true;
            this.errorMessage = error.error.reason
            if(this.errorMessage === undefined)
              this.errorMessage = error.message;
           }
          
          )
  }
  edit():void{
      let userC = new UserC(0,"","","",[],"");
      userC.email= this.user.email;
      userC.firstName = this.user.firstName;
      userC.lastName = this.user.lastName;
      userC.password = "**********";
      if(this.canC){
            userC.permissions.push("can_create_users")
      }
      if(this.canR){
            userC.permissions.push("can_read_users")
            }
      if(this.canU){
            userC.permissions.push("can_update_users")
            }
      if(this.canD){
            userC.permissions.push("can_delete_users")
            }
      if(this.canSM){
        userC.permissions.push("can_search_machines")
        }
      if(this.canS){
            userC.permissions.push("can_start_machines")
            }
      if(this.canST){
            userC.permissions.push("can_stop_machines")
            }
      if(this.canCM){
            userC.permissions.push("can_create_machines")
            }
      if(this.canDM){
            userC.permissions.push("can_destroy_machines")
      }
      if(this.canRM){
            userC.permissions.push("can_restart_machines")
            }
      this.user.permissions = userC.permissions;
      this.httpService.updateUser("http://localhost:8080/api/user/update/" + this.id,userC,this.userService.getToken()).subscribe(
        (response) => {this.user = response;
        this.router.navigateByUrl("users");
      },(error) => {
        this.error = true;
        this.errorMessage = error.error.reason
        if(this.errorMessage === undefined)
          this.errorMessage = error.message;
       }
      )

    }
}
