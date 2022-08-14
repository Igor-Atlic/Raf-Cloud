import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import { UsersComponent } from './components/users/users.component';
import { AdduserComponent } from './components/adduser/adduser.component';
import { EdituserComponent } from './components/edituser/edituser.component';
import { MachineComponent } from './components/machine/machine.component';
import { AddmachineComponent } from './components/addmachine/addmachine.component';
import { ScheduleComponent } from './components/schedule/schedule.component';
import { ErrorComponent } from './components/error/error.component';
import {ReadGuard} from "./guard/read.guard"
import {CreateGuard} from "./guard/create.guard"
import {UpdateGuard} from "./guard/update.guard"
import { AddmachineGuard } from './guard/addmachine.guard';
import { MachineGuard } from './guard/machine.guard';
import { ScheduleGuard } from './guard/schedule.guard';


const routes: Routes = [
  {
      path: "",
      component: HomeComponent
  },
  {
       path: "users",
       component: UsersComponent,
       canActivate: [ReadGuard]
  },
  {
        path: "addUser",
        component: AdduserComponent,
        canActivate: [CreateGuard]
  },
  {
        path: "editUser/:id",
        component: EdituserComponent,
        canActivate: [UpdateGuard]
  },
  {
        path: "machine",
        component: MachineComponent,
        canActivate: [MachineGuard]
  },
      {
      path: "addMachine",
      component: AddmachineComponent,
      canActivate: [AddmachineGuard]

      },{
            path: "schedule/:id",
            component: ScheduleComponent,
            canActivate: [ScheduleGuard]
      },
      {
            path: "error",
            component: ErrorComponent,
            canActivate: [MachineGuard]
      }
      

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
