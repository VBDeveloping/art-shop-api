import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface PaymentResponse {
  id: number;
  orderId: number;
  amount: number;
  type: string;
  status: string;
  paymentDate: string;
  notes: string;
}

export interface PaymentRequest {
  orderId: number;
  amount: number;
  type: string;
  notes?: string;
}

@Injectable({ providedIn: 'root' })
export class PaymentService {

  private readonly API_URL = 'http://localhost:8080/api/payments';

  constructor(private http: HttpClient) {}

  getByOrder(orderId: number): Observable<PaymentResponse[]> {
    return this.http.get<PaymentResponse[]>(`${this.API_URL}/order/${orderId}`);
  }

  create(payment: PaymentRequest): Observable<PaymentResponse> {
    return this.http.post<PaymentResponse>(this.API_URL, payment);
  }

  cancel(id: number): Observable<PaymentResponse> {
    return this.http.patch<PaymentResponse>(`${this.API_URL}/${id}/cancel`, {});
  }
}
