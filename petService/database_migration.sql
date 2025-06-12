-- Lost Pet Alerts Database Migration Script
-- Run this script to add the lost pet alerts functionality to your database

-- 1. Add status column to existing pets table
ALTER TABLE pets ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'SAFE';

-- 2. Update existing pets to have SAFE status if null
UPDATE pets SET status = 'SAFE' WHERE status IS NULL;

-- 3. Create lost_pet_alerts table
CREATE TABLE IF NOT EXISTS lost_pet_alerts (
    id SERIAL PRIMARY KEY,
    pet_id INTEGER NOT NULL,
    owner_id INTEGER NOT NULL,
    last_seen_location VARCHAR(255) NOT NULL,
    description TEXT,
    contact_info VARCHAR(255),
    reward_amount DECIMAL(10,2),
    alert_created TIMESTAMP NOT NULL,
    alert_updated TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    found_location VARCHAR(255),
    found_by VARCHAR(255),
    found_date TIMESTAMP,
    
    -- Foreign key constraints (uncomment if you have foreign key relationships)
    -- FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE,
    -- FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE,
    
    -- Indexes for better performance
    CONSTRAINT lost_pet_alerts_status_check CHECK (status IN ('ACTIVE', 'RESOLVED', 'CANCELLED'))
);

-- 4. Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_lost_pet_alerts_pet_id ON lost_pet_alerts(pet_id);
CREATE INDEX IF NOT EXISTS idx_lost_pet_alerts_owner_id ON lost_pet_alerts(owner_id);
CREATE INDEX IF NOT EXISTS idx_lost_pet_alerts_status ON lost_pet_alerts(status);
CREATE INDEX IF NOT EXISTS idx_lost_pet_alerts_created ON lost_pet_alerts(alert_created);
CREATE INDEX IF NOT EXISTS idx_lost_pet_alerts_location ON lost_pet_alerts(last_seen_location);
CREATE INDEX IF NOT EXISTS idx_pets_status ON pets(status);
CREATE INDEX IF NOT EXISTS idx_pets_owner_id_status ON pets(owner_id, status);

-- 5. Add check constraint for pet status
ALTER TABLE pets ADD CONSTRAINT IF NOT EXISTS pets_status_check 
    CHECK (status IN ('SAFE', 'LOST', 'FOUND'));

-- Insert sample data for testing (optional - remove in production)
-- INSERT INTO lost_pet_alerts (pet_id, owner_id, last_seen_location, description, contact_info, reward_amount, alert_created, alert_updated, status)
-- VALUES 
--     (1, 1, 'Central Park, New York', 'Golden Retriever named Max, very friendly', 'contact@example.com', 500.00, NOW(), NOW(), 'ACTIVE'),
--     (2, 2, 'Brooklyn Bridge Park', 'Small tabby cat, answers to Whiskers', 'cat.owner@example.com', 200.00, NOW(), NOW(), 'ACTIVE');

COMMIT;
