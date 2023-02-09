import { Injectable } from '@angular/core';
import {SellingApiService} from "./selling-api.service";

@Injectable({
  providedIn: 'root'
})
export class SellingService {

  constructor(private sellingApiService: SellingApiService) { }

  getAllUser(status: any){
    return this.sellingApiService.getAllUser(status);
  }

  getAllProduct(status: any){
    return this.sellingApiService.getAllProduct(status);
  }

  findProByCate(categoryId: any){
    return this.sellingApiService.findProByCate(categoryId);
  }

  getAllCategories(status: any){
    return this.sellingApiService.getAllCategories(status);
  }

  // delete code here
  // getAllCategories(){
  //   return this.sellingApiService.getAllCategories();
  // }

  // getProByCate(id: any){
  //   return this.sellingApiService.getProByCate(id);
  // }

  // getProductDetail(id: any){
  //   return this.sellingApiService.getProductDetail(id);
  // }

  // paymentSelling(obj:any){
  //   return this.sellingApiService.paymentSelling(obj);
  // }

  resetQuantityInventory(lst:any){
    return this.sellingApiService.resetQuantityInventory(lst);
  }
}
