import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiConstant} from "../../constants/ApiConstant";

@Injectable({
  providedIn: 'root'
})
export class SellingApiService {

  api = ApiConstant.api;

  constructor(private readonly http: HttpClient) { }

  //selling

  getAllUser(status: any): Observable<any>{
    return this.http.get(`${ApiConstant.selling}/user/${status}`)
  }

  getAllProduct(status: any): Observable<any>{
    return this.http.get(`${ApiConstant.selling}/product/${status}`);
  }

  findProByCate(categoryId: any): Observable<any>{
    return this.http.get(`${ApiConstant.selling}/product/category/${categoryId}`)
  }

  getAllCategories(status: any): Observable<any>{
    return this.http.get(`${ApiConstant.selling}/category/${status}`);
}

  //end

  // delete code here
  // getAllCategories(): Observable<any>{
  //   return this.http.get(`${this.api}category`)
  // }

  // getProByCate(id: any): Observable<any>{
  //   return this.http.get(`${this.api}product/findByCate/${id}`)
  // }

  // getProductDetail(id: any): Observable<any>{
  //   return this.http.get(`${this.api}productDetail/getByProduct/${id}`)
  // }

  // paymentSelling(obj: any): Observable<any>{
  //   return this.http.post(`${this.api}selling/payment`,obj);
  // }

  resetQuantityInventory(lst: any):Observable<any>{
    return this.http.post(`${this.api}selling/resetQuantityInventory`,lst);
  }
}
