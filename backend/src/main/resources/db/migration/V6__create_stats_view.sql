CREATE MATERIALIZED VIEW wallet_statistics AS
SELECT
    w.id AS wallet_id,
    w.user_id,
    COUNT(t.id) AS total_trades,
    COALESCE(SUM(c.commission_value), 0) AS total_commissions_paid,
    -- (sprzedaż - kupno)
    CAST(
        SUM(CASE WHEN t.action = 'SELL' THEN (t.price_qty * t.quantity) ELSE 0 END) -
        SUM(CASE WHEN t.action = 'BUY' THEN (t.price_qty * t.quantity) ELSE 0 END) AS NUMERIC(24, 8))
    AS volume_balance
FROM
    wallets w
        LEFT JOIN
    transactions t ON w.id = t.wallet_id AND t.status = 'COMPLETED'
        LEFT JOIN
    commissions c ON t.id = c.transaction_id
GROUP BY
    w.id, w.user_id;

CREATE UNIQUE INDEX idx_wallet_stats_wallet_id ON wallet_statistics(wallet_id);