import { Component, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { ArtworkService } from '../../../core/services/artwork.service';
import { UserContextService } from '../../../core/services/user-context.service';
import { ArtworkResponse } from '../../../core/models/art-order.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-artwork-list',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, FormsModule, AppHeaderComponent],
  templateUrl: './artwork-list.component.html',
  styleUrl: './artwork-list.component.scss'
})
export class ArtworkListComponent implements OnInit {

  artworks: ArtworkResponse[] = [];
  loading = true;
  errorMessage = '';
  showForm = false;

  newArtwork = {
    title: '',
    artist: '',
    description: '',
    price: null as number | null,
    stockQuantity: null as number | null,
    imageUrl: '',
    source: 'STORE_INVENTORY' as string
  };

  sources = [
    { value: 'STORE_INVENTORY', label: 'Estoque da loja' },
    { value: 'CUSTOMER_OWNED', label: 'Cliente' },
    { value: 'PRINT', label: 'Impressão' }
  ];

  constructor(
    private artworkService: ArtworkService,
    public userContext: UserContextService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.artworkService.findAll().subscribe({
      next: (data) => { this.artworks = data; this.loading = false; },
      error: () => { this.errorMessage = 'Erro ao carregar obras.'; this.loading = false; }
    });
  }

  save(): void {
    this.artworkService.create(this.newArtwork as any).subscribe({
      next: () => {
        this.showForm = false;
        this.newArtwork = { title: '', artist: '', description: '', price: null, stockQuantity: null, imageUrl: '', source: 'STORE_INVENTORY' };
        this.load();
      },
      error: () => this.errorMessage = 'Erro ao salvar obra.'
    });
  }

  addStock(id: number, quantity: number): void {
    this.artworkService.addStock(id, quantity).subscribe({ next: () => this.load() });
  }

  back(): void {
    this.router.navigate(['/dashboard']);
  }
}
