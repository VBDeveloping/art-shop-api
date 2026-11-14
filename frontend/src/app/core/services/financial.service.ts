import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ArtOrderResponse } from '../models/art-order.model';

export interface FinancialSummary {
  totalOrders: number;
  totalRevenue: number;
  averageOrderValue: number;
  orders: ArtOrderResponse[];
}

@Injectable({ providedIn: 'root' })
export class FinancialService {

  private readonly API_URL = 'http://localhost:8080/api/art-orders';

  constructor(private http: HttpClient) {}

  getOrdersInPeriod(from: string, to: string): Observable<FinancialSummary> {
    return this.http.get<ArtOrderResponse[]>(this.API_URL).pipe(
      map(orders => {
        const fromDate = new Date(from);
        const toDate = new Date(to);
        toDate.setHours(23, 59, 59);

        const filtered = orders.filter(o => {
          const orderDate = new Date(o.orderDate);
          return orderDate >= fromDate && orderDate <= toDate;
        });

        const totalRevenue = filtered.reduce((sum, o) => sum + (Number(o.totalValue) || 0), 0);

        return {
          totalOrders: filtered.length,
          totalRevenue,
          averageOrderValue: filtered.length > 0 ? totalRevenue / filtered.length : 0,
          orders: filtered
        };
      })
    );
  }
}
