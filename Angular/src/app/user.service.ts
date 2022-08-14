import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {


  private token: string;
  private permissions: string[];
  private read: string;
  private create: string;
  private delete: string;
  private update: string;
  private readM: string;
  private createM: string;
  private deleteM: string;
  private start: string;
  private stop: string;
  private restart: string;

  constructor() {
   this.token = "";
   this.permissions = [];
   this.read = "can_read_users";
   this.update = "can_update_users";
   this.create = "can_create_users";
   this.delete = "can_delete_users";
   this.readM = "can_search_machines";
   this.createM = "can_create_machines";
   this.deleteM = "can_destroy_machines";
   this.start = "can_start_machines";
   this.stop = "can_stop_machines";
   this.restart = "can_restart_machines"
   }
  getRead(): string{
           return this.read;
    }
  getCreate(): string{
           return this.create;
    }
  getUpdate(): string{
           return this.update;
    }
  getDelete(): string{
           return this.delete;
    }
  getToken(): string{
         return this.token;
  }
  getReadM(): string{
    return this.readM;
}
  getCreateM(): string{
      return this.createM;
  }
  getDeleteM(): string{
      return this.deleteM;
  }
  getStart(): string{
    return this.start;
  }
  getStop(): string{
    return this.stop;
  }
  getRestart(): string{
    return this.restart;
  }
  setToken(token: string): void{
           this.token = token;
           localStorage.setItem('token', this.token);
  }

  getPermissions(): string[]{
    return this.permissions;
  }

  setPermissions(per: string[]): void{
      this.permissions = per;
      localStorage.setItem('permissions',  JSON.stringify(this.permissions));
  }
}
