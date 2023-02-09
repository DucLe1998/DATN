import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { SellingComponent } from "../selling.component";
import { SellingService } from "../../../../../shared/service/selling/selling.service";
import { logging } from "protractor";
import { quantity } from "chartist";
import { Constant } from "../../../../../shared/constants/Constant";
import { FormControl } from "@angular/forms";
import { ProductDetailService } from '../../../../../shared/service/productDetail/product-detail.service';
import { ToastrService } from 'ngx-toastr';

@Component({
    selector: 'product-detail-order',
    templateUrl: './product-detail-order.component.html',
    styleUrls: ['./product-detail-order.component.scss']
})
export class ProductDetailOrderComponent implements OnInit {

    constructor(private matDialogRef: MatDialogRef<SellingComponent>,
        private toastrService: ToastrService,
        private sellingService: SellingService,
        private productDetailService: ProductDetailService,
        @Inject(MAT_DIALOG_DATA) public dataDialog?: any
    ) {
    }

    isSelectColor: boolean = true;
    product: any;
    listSizeOfProduct: any;
    listColorOfProduct: any;
    color: any = [];
    size: any = [];
    // SỐ lượng trong kho
    quantityInventory: number = 0;
    // Số lượng order
    quantityinput: any = 1;
    // Số lượng order trước khi thay đổi
    quantityOld: any;
    productDetailOrder: any = {};
    //Index tabs
    colorSelected: any = '';
    sizeSelected: any = '';

    productDetail: any;


    message = '';

    ngOnInit(): void {
        console.log(this.dataDialog);

        this.getAllSizeAndColor()

        this.listSizeOfProduct = [];
        this.listColorOfProduct = [];
        // let colorOfProduct = new Map();
        // let sizeOfProduct = new Map();
        this.product = this.dataDialog.product;
        // if (this.product.size === undefined || this.product.color === undefined) {
        //     this.sellingService.getProductDetail(this.product.id).subscribe(
        //         resp => {
        //             this.product.productDetail = resp;
        //             this.product.productDetail.forEach(
        //                 detail => {
        //                     colorOfProduct.set(detail.colorId, detail.nameColor);
        //                     sizeOfProduct.set(detail.sizeId, detail.nameSize);
        //                 }
        //             )
        //             colorOfProduct.forEach((value, key) => {
        //                 this.listColorOfProduct.push({id: key, code: value})
        //             });
        //             sizeOfProduct.forEach((value, key) => {
        //                 this.listSizeOfProduct.push({id: key, code: value})
        //             });
        //         }
        //     )
        // }
    }

    selectColor(item: any) {
        this.sizeSelected = '';
        this.isSelectColor = false;
        this.colorSelected = item;
        this.quantityInventory = 0;
        this.productDetail = {};
        this.productDetailService.getSizeByProductId(this.dataDialog.product.id, this.colorSelected).subscribe({
            next: res => {
                this.listSizeOfProduct = res;
            }
        })

    }

    selectSize(item: any) {
        this.sizeSelected = item;
        console.log(this.sizeSelected);
        console.log(this.colorSelected);
        this.productDetailService.getByColorAndSize(this.colorSelected, this.sizeSelected).subscribe({
            next: res => {
                console.log(res);
                this.productDetail = res;
                this.quantityInventory = res.quantity;
                console.log(this.quantityInventory);
            }
        })
    }

    getAllSizeAndColor() {
        this.productDetailService.getColorByProductId(this.dataDialog.product.id).subscribe({
            next: res => {
                this.listColorOfProduct = res;
                console.log(this.listColorOfProduct);

            }
        })

    }



    onDismiss() {
        this.matDialogRef.close();
    }

    onSubmit() {
        if (this.colorSelected == '' || this.sizeSelected == '') {
            this.toastrService.warning('Vui lòng chọn đầy đủ thông tin');
            return;
        }
        if (this.quantityinput > 0 && this.quantityinput <= this.quantityInventory) {
            // this.productDetailOrder.product = this.product;
            this.productDetailOrder.quantityOrder = parseInt(this.quantityinput);
            this.productDetailOrder.price = this.product.unitPrice;
            this.productDetailOrder.quantityInventory = this.quantityInventory;
            this.productDetailOrder.productName = this.product.productName;
            this.productDetailOrder.size = this.sizeSelected;
            this.productDetailOrder.color = this.colorSelected;
            this.productDetailOrder.id = this.productDetail.id;
            // this.productDetailOrder.weight = this.product.weight;
            console.log(this.productDetailOrder);

            this.matDialogRef.close(this.productDetailOrder);
        } else {

            this.message = 'Số lượng không hợp lệ';
        }
        // Do filter trả về 1 mảng nên phải 

    }


    checkQuantityInput() {
        this.message = '';
        // let pattern = /^[0-9]*$/
        // if (this.quantityinput == ''){
        //     this.quantityinput = 1;
        // }
        // if (pattern.test(this.quantityinput)) {
        //     this.quantityinput = parseInt(this.quantityinput);
        //     if (this.quantityinput > this.quantityInventory) {
        //         this.quantityinput = this.quantityInventory
        //     }
        //     this.quantityOld = this.quantityinput
        // } else {
        //     this.quantityinput = this.quantityOld;
        // }
    }

    plusQuantity() {
        if (this.quantityinput < this.quantityInventory) {
            this.quantityinput += 1;
        }
    }

    minusQuantity() {
        if (this.quantityinput > 1) {
            this.quantityinput -= 1;
        }
    }

}
