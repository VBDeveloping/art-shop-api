import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { Router } from '@angular/router';
import { ArtOrderService } from '../../../core/services/art-order.service';
import { ArtOrderResponse } from '../../../core/models/art-order.model';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-order-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, DatePipe],
  templateUrl: './order-list.component.html',
  styleUrl: './order-list.component.scss'
})
export class OrderListComponent implements OnInit {

  orders: ArtOrderResponse[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private artOrderService: ArtOrderService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.artOrderService.findAll().subscribe({
      next: (data) => {
        this.orders = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar pedidos.';
        this.loading = false;
      }
    });
  }

  logout(): void {
    this.authService.logout();
  }

  newOrder(): void {
    this.router.navigate(['/orders/new']);
  }

  navigate(path: string): void {
    this.router.navigate([path]);
  }
}
