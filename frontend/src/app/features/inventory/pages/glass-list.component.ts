import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';
import { GlassService } from '../../../core/services/glass.service';
import { GlassResponse } from '../../../core/models/art-order.model';

@Component({
  selector: 'app-glass-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './glass-list.component.html',
  styleUrl: './glass-list.component.scss'
})
export class GlassListComponent implements OnInit {

  glasses: GlassResponse[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private glassService: GlassService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.glassService.findAll().subscribe({
      next: (data) => {
        this.glasses = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar vidros.';
        this.loading = false;
      }
    });
  }

  back(): void {
    this.router.navigate(['/orders']);
  }
}
