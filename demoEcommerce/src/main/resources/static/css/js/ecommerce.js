// Mobile menu toggle
document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.getElementById('menuToggle');
    const mainNav = document.querySelector('.main-nav');
    
    if (menuToggle) {
      menuToggle.addEventListener('click', function() {
        if (mainNav.style.display === 'flex') {
          mainNav.style.display = 'none';
        } else {
          mainNav.style.display = 'flex';
          mainNav.style.position = 'absolute';
          mainNav.style.top = '4rem';
          mainNav.style.left = '0';
          mainNav.style.width = '100%';
          mainNav.style.backgroundColor = 'white';
          mainNav.style.padding = '1rem';
          mainNav.style.boxShadow = '0 4px 6px rgba(0, 0, 0, 0.1)';
        }
      });
    }
    
    // Add to cart functionality
    const addToCartButtons = document.querySelectorAll('.btn-small');
    const cartBadge = document.querySelector('.cart-badge');
    
    let cartCount = 3; // Initial cart count
    
    addToCartButtons.forEach(button => {
      button.addEventListener('click', function() {
        cartCount++;
        cartBadge.textContent = cartCount;
        
        // Animation effect
        cartBadge.style.transform = 'scale(1.5)';
        setTimeout(() => {
          cartBadge.style.transform = 'scale(1)';
        }, 300);
        
        // Show added to cart message
        const productCard = this.closest('.product-card');
        const productTitle = productCard.querySelector('.product-title').textContent;
        
        const message = document.createElement('div');
        message.textContent = `${productTitle} added to cart!`;
        message.style.position = 'fixed';
        message.style.bottom = '20px';
        message.style.right = '20px';
        message.style.backgroundColor = 'var(--color-teal)';
        message.style.color = 'white';
        message.style.padding = '10px 20px';
        message.style.borderRadius = 'var(--border-radius)';
        message.style.zIndex = '100';
        
        document.body.appendChild(message);
        
        setTimeout(() => {
          message.style.opacity = '0';
          message.style.transition = 'opacity 0.5s ease';
          setTimeout(() => {
            document.body.removeChild(message);
          }, 500);
        }, 2000);
      });
    });
    
    // Wishlist functionality
    const wishlistButtons = document.querySelectorAll('.wishlist-button');
    
    wishlistButtons.forEach(button => {
      button.addEventListener('click', function() {
        const svg = this.querySelector('svg');
        
        if (svg.getAttribute('fill') === 'none') {
          svg.setAttribute('fill', 'currentColor');
        } else {
          svg.setAttribute('fill', 'none');
        }
      });
    });
    
    // Newsletter subscription
    const subscriptionForm = document.querySelector('.subscription-form');
    const subscriptionInput = document.querySelector('.subscription-input');
    
    if (subscriptionForm) {
      subscriptionForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        if (subscriptionInput.value.trim() === '') {
          alert('Please enter your email address');
          return;
        }
        
        // Email validation
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(subscriptionInput.value)) {
          alert('Please enter a valid email address');
          return;
        }
        
        // Success message
        const formParent = subscriptionForm.parentElement;
        subscriptionForm.style.display = 'none';
        
        const successMessage = document.createElement('p');
        successMessage.textContent = 'Thank you for subscribing to our newsletter!';
        successMessage.style.backgroundColor = 'var(--color-mint)';
        successMessage.style.color = 'var(--color-teal)';
        successMessage.style.padding = '1rem';
        successMessage.style.borderRadius = 'var(--border-radius)';
        successMessage.style.marginTop = '1rem';
        
        formParent.appendChild(successMessage);
      });
    }
    
    // Image hover effect for products
    const productCards = document.querySelectorAll('.product-card');
    
    productCards.forEach(card => {
      card.addEventListener('mouseenter', function() {
        const image = this.querySelector('.product-image');
        image.style.transform = 'scale(1.05)';
      });
      
      card.addEventListener('mouseleave', function() {
        const image = this.querySelector('.product-image');
        image.style.transform = 'scale(1)';
      });
    });
  });