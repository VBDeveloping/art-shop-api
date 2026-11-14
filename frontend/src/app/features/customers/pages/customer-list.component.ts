import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomerService } from '../../../core/services/customer.service';
import { CustomerResponse } from '../../../core/models/customer.model';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';


@Component({
  selector: 'app-customer-list',
  standalone: true,
  imports: [CommonModule, FormsModule, AppHeaderComponent],
  templateUrl: './customer-list.component.html',
  styleUrl: './customer-list.component.scss'
})
export class CustomerListComponent {

  customers: CustomerResponse[] = [];
  searchTerm = '';
  searched = false;
  loading = false;
  errorMessage = '';

  constructor(
    private customerService: CustomerService,
    private router: Router
  ) {}

  get canSearch(): boolean {
    return this.searchTerm.trim().length >= 3;
  }

  search(): void {
    if (!this.canSearch) return;
    this.loading = true;
    this.errorMessage = '';
    this.customerService.findAll().subscribe({
      next: (data) => {
        const q = this.searchTerm.toLowerCase();
        this.customers = data.filter(c =>
          c.name.toLowerCase().includes(q) ||
          c.cpf.includes(q) ||
          c.email.toLowerCase().includes(q) ||
          c.phone.includes(q)
        );
        this.searched = true;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao buscar clientes.';
        this.loading = false;
      }
    });
  }

  onSearchInput(): void {
    if (this.searchTerm.trim().length < 3) {
      this.customers = [];
      this.searched = false;
    }
  }

  newCustomer(): void {
    this.router.navigate(['/customers/new']);
  }

  edit(id: number): void {
    this.router.navigate(['/customers', id, 'edit']);
  }

  toggleStatus(id: number): void {
    this.customerService.toggleStatus(id).subscribe({
      next: () => this.search()
    });
  }

  back(): void {
    this.router.navigate(['/dashboard']);
  }
}
