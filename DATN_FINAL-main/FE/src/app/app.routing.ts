import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {BrowserModule} from '@angular/platform-browser';
import {Routes, RouterModule} from '@angular/router';
import {AdminLayoutComponent} from './layouts/admin-layout/admin-layout.component';
import {AuthGuard} from './shared/guard/auth.guard';

const routes: Routes = [
    {
        path: '',
        redirectTo: 'selling',
        pathMatch: 'full',
    },
    {
        path: 'selling',
        loadChildren: () => import('./pages/admin/selling/selling.module').then(m => m.SellingModule),
        // canActivate: [AuthGuard],
    },
    {
        path: 'login',
        loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule),
    }
];

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        RouterModule.forRoot(routes, {
            useHash: true
        })
    ],
    exports: [],
})
export class AppRoutingModule {
}
