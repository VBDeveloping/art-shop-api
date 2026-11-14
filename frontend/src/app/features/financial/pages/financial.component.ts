import { Component } from '@angular/core';
import { CommonModule, CurrencyPipe, DatePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { FinancialService, FinancialSummary } from '../../../core/services/financial.service';
import { AppHeaderComponent } from '../../../shared/components/organisms/app-header/app-header.component';

@Component({
  selector: 'app-financial',
  standalone: true,
  imports: [CommonModule, FormsModule, CurrencyPipe, DatePipe, AppHeaderComponent],
  templateUrl: './financial.component.html',
  styleUrl: './financial.component.scss'
})
export class FinancialComponent {

  fromDate = '';
  toDate = '';
  loading = false;
  errorMessage = '';
  summary: FinancialSummary | null = null;

  // Atalhos de período
  shortcuts = [
    { label: 'Hoje', days: 0 },
    { label: 'Ontem', days: 1 },
    { label: 'Últimos 7 dias', days: 7 },
    { label: 'Últimos 30 dias', days: 30 },
  ];

  paymentTypeLabels: Record<string, string> = {
    PIX: 'PIX',
    CASH: 'Dinheiro',
    CREDIT_CARD: 'Cartão de Crédito',
    DEBIT_CARD: 'Cartão de Débito'
  };

  statusLabels: Record<string, string> = {
    PENDING: 'Aguardando',
    IN_PROGRESS: 'Em produção',
    READY: 'Pronto',
    DELIVERED: 'Entregue'
  };

  constructor(
    private financialService: FinancialService,
    private router: Router
  ) {
    // Default: hoje
    const today = new Date().toISOString().split('T')[0];
    this.fromDate = today;
    this.toDate = today;
  }

  applyShortcut(days: number): void {
    const today = new Date();
    const from = new Date();
    from.setDate(today.getDate() - days);
    this.fromDate = from.toISOString().split('T')[0];
    this.toDate = today.toISOString().split('T')[0];
    this.search();
  }

  search(): void {
    if (!this.fromDate || !this.toDate) return;
    this.loading = true;
    this.errorMessage = '';
    this.summary = null;

    this.financialService.getOrdersInPeriod(this.fromDate, this.toDate).subscribe({
      next: (data) => {
        this.summary = data;
        this.loading = false;
      },
      error: () => {
        this.errorMessage = 'Erro ao carregar dados financeiros.';
        this.loading = false;
      }
    });
  }
}
