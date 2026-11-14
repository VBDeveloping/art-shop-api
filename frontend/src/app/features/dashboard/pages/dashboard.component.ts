import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { ArtOrderService } from '../../../core/services/art-order.service';
import { UserContextService } from '../../../core/services/user-context.service';
import { ArtOrderResponse } from '../../../core/models/art-order.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, DatePipe, AppHeaderComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit {

  orders: ArtOrderResponse[] = [];
  loading = true;

  get pendingOrders() {
    return this.orders.filter(o => o.status === 'PENDING');
  }

  get inProgressOrders() {
    return this.orders.filter(o => o.status === 'IN_PROGRESS');
  }

  get readyOrders() {
    return this.orders.filter(o => o.status === 'READY');
  }

  get urgentOrders() {
    const today = new Date();
    const threeDaysFromNow = new Date();
    threeDaysFromNow.setDate(today.getDate() + 3);
    return this.orders.filter(o => {
      if (o.status === 'DELIVERED') return false;
      const delivery = new Date(o.deliveryDate);
      return delivery <= threeDaysFromNow;
    });
  }

  get totalRevenue() {
    return this.orders
      .filter(o => o.status !== 'PENDING')
      .reduce((sum, o) => sum + (o.totalValue || 0), 0);
  }

  constructor(
    private artOrderService: ArtOrderService,
    public userContext: UserContextService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.artOrderService.findAll().subscribe({
      next: (data) => {
        this.orders = data;
        this.loading = false;
      },
      error: () => this.loading = false
    });
  }

  isUrgent(order: ArtOrderResponse): boolean {
    const today = new Date();
    const delivery = new Date(order.deliveryDate);
    const diff = (delivery.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);
    return diff <= 1;
  }

  isWarning(order: ArtOrderResponse): boolean {
    const today = new Date();
    const delivery = new Date(order.deliveryDate);
    const diff = (delivery.getTime() - today.getTime()) / (1000 * 60 * 60 * 24);
    return diff > 1 && diff <= 3;
  }

  navigate(path: string): void {
    this.router.navigate([path]);
  }

  logout(): void {
    this.userContext.clear();
    this.router.navigate(['/login']);
  }
}
