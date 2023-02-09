import { Injectable } from '@angular/core';
import { ProductDetailApiService } from './product-detail-api.service';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailService {

constructor(
  private readonly apiService: ProductDetailApiService
) { }
createProductDetail(data: any){
   return this.apiService.createProductDetail(data);
}

getColorByProductId(productId: any){
  return this.apiService.getColorByProductId(productId);
}

getSizeByProductId(productId: any, color: any){
  return this.apiService.getSizeByProductId(productId, color);
}
getByColorAndSize(color: any, size: any){
  return this.apiService.getByColorAndSize(color, size);
}

getProductDetailById(data: any){
  return this.apiService.getProductDetailById(data);
}
getOneProductDetail(id: any){
  return this.apiService.getOneProductDetail(id);
}
updateProductDetail(data: any){
  return this.apiService.updateProductDetail(data);
}
dowloadExcel(){
  return this.apiService.dowloadExcel();
}
}
