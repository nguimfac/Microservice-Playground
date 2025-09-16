-- Création base de données pour Order Service
CREATE DATABASE IF NOT EXISTS order_db;
CREATE USER IF NOT EXISTS 'order_user'@'%' IDENTIFIED BY 'order_pass';
GRANT ALL PRIVILEGES ON order_db.* TO 'order_user'@'%';

-- Création base de données pour Inventory Service
CREATE DATABASE IF NOT EXISTS inventory_db;
CREATE USER IF NOT EXISTS 'inventory_user'@'%' IDENTIFIED BY 'inventory_pass';
GRANT ALL PRIVILEGES ON inventory_db.* TO 'inventory_user'@'%';

-- Appliquer les changements
FLUSH PRIVILEGES;
-- Vérification (verifier l'existence des user en logs)
SELECT user, host FROM mysql.user WHERE user IN ('order_user', 'inventory_user');
