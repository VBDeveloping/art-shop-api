import { Routes } from '@angular/router';
import { DashboardComponent } from './features/dashboard/pages/dashboard.component';
import { OrderListComponent } from './features/orders/pages/order-list.component';
import { OrderFormComponent } from './features/orders/pages/order-form.component';
import { ArtworkListComponent } from './features/inventory/pages/artwork-list.component';
import { FrameListComponent } from './features/inventory/pages/frame-list.component';
import { GlassListComponent } from './features/inventory/pages/glass-list.component';
import { CustomerListComponent } from './features/customers/pages/customer-list.component';
import { CustomerFormComponent } from './features/customers/pages/customer-form.component';
import { AttendanceComponent } from './features/attendance/pages/attendance.component';
import { UserListComponent } from './features/users/pages/user-list.component';
import { FinancialComponent } from './features/financial/pages/financial.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login.component').then(m => m.LoginComponent)
  },
  { path: 'dashboard', component: DashboardComponent, canActivate: [authGuard]},
  { path: 'orders', component: OrderListComponent, canActivate: [authGuard] },
  { path: 'orders/new', component: OrderFormComponent, canActivate: [authGuard] },
  { path: 'inventory/artworks', component: ArtworkListComponent, canActivate: [authGuard] },
  { path: 'inventory/frames', component: FrameListComponent, canActivate: [authGuard] },
  { path: 'inventory/glasses', component: GlassListComponent, canActivate: [authGuard] },
  { path: 'customers', component: CustomerListComponent, canActivate: [authGuard] },
  { path: 'customers/new', component: CustomerFormComponent, canActivate: [authGuard] },
  { path: 'customers/:id/edit', component: CustomerFormComponent, canActivate: [authGuard] },
  { path: 'attendance/new', component: AttendanceComponent, canActivate: [authGuard] },
  { path: 'financial', component: FinancialComponent, canActivate: [authGuard] },
  { path: 'users', component: UserListComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
];
