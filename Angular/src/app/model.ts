export interface Login{
  jwt: string,
  permissions: string[]
}

export interface Submit{
  username: string,
  password: string
}

export class User{
  userId:number;
  firstName: string;
  lastName: string;
  email: string;
  permissions: string[];
  constructor(userId:number,
                firstName: string,
                lastName: string,
                email: string,
                permissions: string[]){
                this.userId = userId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.permissions = permissions;
  }
}

export class UserC{
  userId:number;
  firstName: string;
  lastName: string;
  email: string;
  permissions: string[];
  password:string;
  constructor(userId:number,
                firstName: string,
                lastName: string,
                email: string,
                permissions: string[],
                password:string){
                this.userId = userId;
                this.firstName = firstName;
                this.lastName = lastName;
                this.email = email;
                this.permissions = permissions;
                this.password = password;
  }
}

export class Machine{
  machineId:number;
  machineName:string;
  status:string;
  createdAt:Date;
  active:boolean;
  mLock:boolean;
  createdBy: User;
  constructor(machineId:number,
              machineName:string,
              status:string,
              createdAt:Date,
              active:boolean,
              mLock:boolean,
              createdBy: User){
                this.machineId = machineId;
                this.machineName = machineName;
                this.status = status;
                this.createdAt = createdAt;
                this.active = active;
                this.mLock = mLock;
                this.createdBy = createdBy;
              }

}
export class MachineAdd{
  machineName:string;
  constructor(machineName:string){this.machineName = machineName;}
}

export class Search{
  name:string;
  status:string[];
  dateFrom:string;
  dateTo:string;
  constructor(
              name:string,
              status:string[],
              dateFrom:string,
              dateTo:string,
              ){
                this.name = name;
                this.status = status;
                this.dateFrom = dateFrom;
                this.dateTo = dateTo;
                
              }

}

export class Sch{
  machineId:number;
  date:number[];
  fun:string;
  constructor(
    machineId:number,
    date:number[],
    fun:string
    ){
      this.machineId = machineId;
      this.date = date;
      this.fun = fun;
    }
}

export class Errors{
  errorId:number;
  machineId:number;
  fun: string;
  date: Date;
  message:string;
  createdBy: User;
  constructor(errorId:number,
    message:string,
           
              createdBy: User,machineId:number,fun: string,date: Date){
                this.errorId = errorId;
                this.message = message;
                this.machineId = machineId;
                this.fun = fun;
                this.date = date;
                this.createdBy = createdBy;
              }

}
