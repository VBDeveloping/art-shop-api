import { Routes } from '@angular/router';
import { OrderListComponent } from './features/orders/pages/order-list.component';
import { OrderFormComponent } from './features/orders/pages/order-form.component';
import { ArtworkListComponent } from './features/inventory/pages/artwork-list.component';
import { FrameListComponent } from './features/inventory/pages/frame-list.component';
import { GlassListComponent } from './features/inventory/pages/glass-list.component';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/pages/login.component').then(m => m.LoginComponent)
  },
  { path: 'orders', component: OrderListComponent, canActivate: [authGuard] },
  { path: 'orders/new', component: OrderFormComponent, canActivate: [authGuard] },
  { path: 'inventory/artworks', component: ArtworkListComponent, canActivate: [authGuard] },
  { path: 'inventory/frames', component: FrameListComponent, canActivate: [authGuard] },
  { path: 'inventory/glasses', component: GlassListComponent, canActivate: [authGuard] },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];
