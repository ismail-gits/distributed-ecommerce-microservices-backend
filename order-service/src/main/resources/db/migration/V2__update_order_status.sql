-- 1. Drop the old check constraint
ALTER TABLE t_orders
DROP CONSTRAINT IF EXISTS t_orders_order_status_check;

-- 2. Add a new check constraint with the new enum value
ALTER TABLE t_orders
ADD CONSTRAINT t_orders_order_status_check
CHECK (order_status IN ('PLACED', 'SHIPPED', 'DELIVERED', 'CANCELLED'));