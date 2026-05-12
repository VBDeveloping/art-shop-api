import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ArtOrderRequest, ArtOrderResponse } from '../models/art-order.model';

@Injectable({
  providedIn: 'root'
})
export class ArtOrderService {

  private readonly API_URL = 'http://localhost:8080/api/art-orders';

  constructor(private http: HttpClient) {}

  findAll(): Observable<ArtOrderResponse[]> {
    return this.http.get<ArtOrderResponse[]>(this.API_URL);
  }

  findById(id: number): Observable<ArtOrderResponse> {
    return this.http.get<ArtOrderResponse>(`${this.API_URL}/${id}`);
  }

  create(order: ArtOrderRequest): Observable<ArtOrderResponse> {
    return this.http.post<ArtOrderResponse>(this.API_URL, order);
  }
}
