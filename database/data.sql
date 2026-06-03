USE online_book_store;

-- Insert Admin and a default User
INSERT INTO users (name, email, password, phone, address, role) VALUES 
('Admin User', 'admin@bookstore.com', 'admin123', '1234567890', 'Bookstore HQ, Mumbai', 'ADMIN'),
('Student User', 'student@mail.com', 'student123', '9876543210', 'College Hostel, Pune', 'USER');

-- Insert Categories
INSERT INTO categories (name) VALUES 
('Technology'), ('Science Fiction'), ('Self-Help'), ('Business'), ('Fiction'), ('Romance'), ('Mystery');

-- Insert 25 realistic Books
INSERT INTO books (title, author, category_id, price, stock, language, publication, rating, description, type) VALUES 
('Effective Java', 'Joshua Bloch', 1, 950.00, 50, 'English', 'Addison-Wesley', 4.8, 'The definitive guide to Java best practices.', 'PHYSICAL'),
('Clean Code', 'Robert C. Martin', 1, 850.00, 30, 'English', 'Prentice Hall', 4.9, 'A Handbook of Agile Software Craftsmanship.', 'PHYSICAL'),
('Java: The Complete Reference', 'Herbert Schildt', 1, 1200.00, 45, 'English', 'McGraw Hill', 4.6, 'Comprehensive coverage of the Java language.', 'EBOOK'),
('Head First Java', 'Kathy Sierra', 1, 899.00, 20, 'English', 'O''Reilly', 4.7, 'A brain-friendly guide to learning Java.', 'PHYSICAL'),
('Spring in Action', 'Craig Walls', 1, 1150.00, 15, 'English', 'Manning', 4.5, 'Practical Spring framework guide.', 'PHYSICAL'),
('The Pragmatic Programmer', 'David Thomas', 1, 1050.00, 25, 'English', 'Addison-Wesley', 4.8, 'Your journey to mastery in coding.', 'EBOOK'),

('Dune', 'Frank Herbert', 2, 600.00, 40, 'English', 'Chilton Books', 4.8, 'Classic science fiction masterpiece.', 'PHYSICAL'),
('Foundation', 'Isaac Asimov', 2, 550.00, 35, 'English', 'Gnome Press', 4.7, 'The greatest saga in science fiction.', 'PHYSICAL'),
('Neuromancer', 'William Gibson', 2, 500.00, 20, 'English', 'Ace', 4.4, 'The novel that defined cyberpunk.', 'PHYSICAL'),
('The Martian', 'Andy Weir', 2, 450.00, 50, 'English', 'Crown Publishing', 4.6, 'A story of survival on Mars.', 'EBOOK'),

('Atomic Habits', 'James Clear', 3, 400.00, 100, 'English', 'Avery', 4.9, 'Tiny changes, remarkable results.', 'PHYSICAL'),
('Deep Work', 'Cal Newport', 3, 350.00, 60, 'English', 'Grand Central', 4.6, 'Rules for focused success in a distracted world.', 'PHYSICAL'),
('The Power of Habit', 'Charles Duhigg', 3, 399.00, 40, 'English', 'Random House', 4.5, 'Why we do what we do in life and business.', 'PHYSICAL'),
('Think and Grow Rich', 'Napoleon Hill', 3, 250.00, 80, 'English', 'Ralston Society', 4.7, 'The landmark bestseller for wealth building.', 'EBOOK'),

('Rich Dad Poor Dad', 'Robert Kiyosaki', 4, 399.00, 90, 'English', 'Warner Books', 4.7, 'What the rich teach their kids about money.', 'PHYSICAL'),
('The Lean Startup', 'Eric Ries', 4, 450.00, 55, 'English', 'Crown Business', 4.6, 'How today''s entrepreneurs use continuous innovation.', 'PHYSICAL'),
('Zero to One', 'Peter Thiel', 4, 500.00, 45, 'English', 'Crown Business', 4.8, 'Notes on startups, or how to build the future.', 'EBOOK'),
('The Intelligent Investor', 'Benjamin Graham', 4, 600.00, 30, 'English', 'HarperBusiness', 4.9, 'The definitive book on value investing.', 'PHYSICAL'),

('The Alchemist', 'Paulo Coelho', 5, 299.00, 120, 'English', 'HarperCollins', 4.7, 'A fable about following your dream.', 'PHYSICAL'),
('To Kill a Mockingbird', 'Harper Lee', 5, 350.00, 65, 'English', 'J.B. Lippincott', 4.9, 'A classic novel of modern American literature.', 'PHYSICAL'),
('1984', 'George Orwell', 5, 300.00, 85, 'English', 'Secker & Warburg', 4.8, 'A dystopian social science fiction novel.', 'EBOOK'),
('Pride and Prejudice', 'Jane Austen', 6, 250.00, 50, 'English', 'T. Egerton', 4.6, 'A romantic novel of manners.', 'PHYSICAL'),

('The Girl with the Dragon Tattoo', 'Stieg Larsson', 7, 450.00, 35, 'English', 'Norstedts', 4.5, 'A psychological thriller novel.', 'PHYSICAL'),
('Gone Girl', 'Gillian Flynn', 7, 400.00, 40, 'English', 'Crown Publishing', 4.4, 'A thriller about a disappeared wife.', 'PHYSICAL'),
('The Da Vinci Code', 'Dan Brown', 7, 500.00, 60, 'English', 'Doubleday', 4.6, 'A mystery thriller following Robert Langdon.', 'EBOOK');
