CREATE INDEX idx_transactions_date_category ON transactions(transaction_date, category);
CREATE INDEX idx_transactions_type ON transactions(transaction_type);
CREATE INDEX idx_transactions_expense_category ON transactions(transaction_type, category);
CREATE INDEX idx_transactions_category_lower ON transactions(LOWER(category));