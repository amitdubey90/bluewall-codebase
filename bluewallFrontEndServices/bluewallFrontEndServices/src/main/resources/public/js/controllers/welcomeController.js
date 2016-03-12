app.controller('welcomeController', function($scope, $ocLazyLoad, $state){
  console.log('inside welcomeController');

  // Animations
  // Home

  // Parallax
  var parallax = function() {
    $(window).stellar();
  };

  var homeAnimate = function() {
    if ( $('#fh5co-home').length > 0 ) {  

      $('#fh5co-home').waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {


          setTimeout(function() {
            $('#fh5co-home .to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          
          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };


  var introAnimate = function() {
    if ( $('#fh5co-intro').length > 0 ) { 

      $('#fh5co-intro').waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {


          setTimeout(function() {
            $('#fh5co-intro .to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInRight animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 1000);

          
          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };

  var workAnimate = function() {
    if ( $('#fh5co-work').length > 0 ) {  

      $('#fh5co-work').waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {


          setTimeout(function() {
            $('#fh5co-work .to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          
          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };


  var testimonialAnimate = function() {
    var testimonial = $('#fh5co-testimonials');
    if ( testimonial.length > 0 ) { 

      testimonial.waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {

          var sec = testimonial.find('.to-animate').length,
            sec = parseInt((sec * 200) - 400);

          setTimeout(function() {
            testimonial.find('.to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          setTimeout(function() {
            testimonial.find('.to-animate-2').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInDown animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, sec);

          
          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };

  var servicesAnimate = function() {
    var services = $('#fh5co-services');
    if ( services.length > 0 ) {  

      services.waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {

          var sec = services.find('.to-animate').length,
            sec = parseInt((sec * 200) + 400);

          setTimeout(function() {
            services.find('.to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          setTimeout(function() {
            services.find('.to-animate-2').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('bounceIn animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, sec);


          
          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };

  var aboutAnimate = function() {
    var about = $('#fh5co-about');
    if ( about.length > 0 ) { 

      about.waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {


          setTimeout(function() {
            about.find('.to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          

          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };

  var countersAnimate = function() {
    var counters = $('#fh5co-counters');
    if ( counters.length > 0 ) {  

      counters.waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {

          var sec = counters.find('.to-animate').length,
            sec = parseInt((sec * 200) + 400);

          setTimeout(function() {
            counters.find('.to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          setTimeout(function() {
            counters.find('.js-counter').countTo({
              formatter: function (value, options) {
                  return value.toFixed(options.decimals);
              },
            });
          }, 400);

          setTimeout(function() {
            counters.find('.to-animate-2').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('bounceIn animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, sec);

          

          

          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };


  var contactAnimate = function() {
    var contact = $('#fh5co-contact');
    if ( contact.length > 0 ) { 

      contact.waypoint( function( direction ) {
                    
        if( direction === 'down' && !$(this.element).hasClass('animated') ) {

          setTimeout(function() {
            contact.find('.to-animate').each(function( k ) {
              var el = $(this);
              
              setTimeout ( function () {
                el.addClass('fadeInUp animated');
              },  k * 200, 'easeInOutExpo' );
              
            });
          }, 200);

          $(this.element).addClass('animated');
            
        }
      } , { offset: '80%' } );

    }
  };

  var windowScroll = function() {
    var lastScrollTop = 0;

    $(window).scroll(function(event){

        var header = $('#fh5co-header'),
        scrlTop = $(this).scrollTop();

      if($state.is('welcome')) {
          if ( scrlTop > 500 && scrlTop <= 2000 ) {
        header.addClass('navbar-fixed-top fh5co-animated slideInDown');
      } else if ( scrlTop <= 500) {
        if ( header.hasClass('navbar-fixed-top') ) {
          header.addClass('navbar-fixed-top fh5co-animated slideOutUp');
          setTimeout(function(){
            header.removeClass('navbar-fixed-top fh5co-animated slideInDown slideOutUp');
          }, 100 );
        }
      } 
      }
      
    });
  };
    
  var animate = function() {
    console.log("animating landing page")
    // Animations
    windowScroll();
    parallax();
    homeAnimate();
    introAnimate();
    workAnimate();
    testimonialAnimate();
    servicesAnimate();
    aboutAnimate();
    countersAnimate();
    contactAnimate();
  };

  animate();
});