import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { Router } from '@angular/router';
import { ArtworkService } from '../../../core/services/artwork.service';
import { ArtworkResponse } from '../../../core/models/art-order.model';

@Component({
  selector: 'app-artwork-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe],
  templateUrl: './artwork-list.component.html',
  styleUrl: './artwork-list.component.scss'
})
export class ArtworkListComponent implements OnInit {

  artworks: ArtworkResponse[] = [];
  loading = true;
  errorMessage = '';

  constructor(
    private artworkService: ArtworkService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.artworkService.findAll().subscribe({
      next: (data) => {
        this.artworks = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar obras.';
        this.loading = false;
      }
    });
  }

  back(): void {
    this.router.navigate(['/orders']);
  }
}
