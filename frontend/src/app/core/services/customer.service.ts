import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CustomerRequest, CustomerResponse } from '../models/customer.model';

@Injectable({ providedIn: 'root' })
export class CustomerService {

  private readonly API_URL = 'http://localhost:8080/api/customers';

  constructor(private http: HttpClient) {}

  findAll(): Observable<CustomerResponse[]> {
    return this.http.get<CustomerResponse[]>(this.API_URL);
  }

  findById(id: number): Observable<CustomerResponse> {
    return this.http.get<CustomerResponse>(`${this.API_URL}/${id}`);
  }

  create(customer: CustomerRequest): Observable<CustomerResponse> {
    return this.http.post<CustomerResponse>(this.API_URL, customer);
  }

  update(id: number, customer: CustomerRequest): Observable<CustomerResponse> {
    return this.http.put<CustomerResponse>(`${this.API_URL}/${id}`, customer);
  }

  toggleStatus(id: number): Observable<void> {
    return this.http.patch<void>(`${this.API_URL}/${id}/toggle-status`, {});
  }
}
