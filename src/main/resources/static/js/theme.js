jQuery(document).ready(function($){
	//if you change this breakpoint in the style.css file (or _layout.scss if you use SASS), don't forget to update this value as well
	var MqL = 1170;
	//move nav element position according to window width
	moveSearchBar();
	$(window).on('resize', function(){
		(!window.requestAnimationFrame) ? setTimeout(moveSearchBar, 300) : window.requestAnimationFrame(moveSearchBar);
	});

	//mobile - open lateral menu clicking on the menu icon
	$('.cd-nav-trigger').on('click', function(event){
		event.preventDefault();
		if( $('.cd-main-content').hasClass('nav-is-visible') ) {
			closeNav();
			$('.cd-overlay').removeClass('is-visible');
		} else {
			$(this).addClass('nav-is-visible');
			$('.cd-main-header').addClass('nav-is-visible');
			$('.cd-main-content').addClass('nav-is-visible').one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
				$('body').addClass('overflow-hidden');
			});
			toggleSearch('close');
			$('.cd-overlay').addClass('is-visible');
		}
	});

	//open search form
	$('.cd-search-trigger').on('click', function(event){
		event.preventDefault();
		toggleSearch();
		closeNav();
	});

	//submenu items - go back link
	$('.go-back').on('click', function(){
		$(this).parent('ul').addClass('is-hidden').parent('.has-children').parent('ul').removeClass('moves-out');
	});

	function closeNav() {
		$('.cd-nav-trigger').removeClass('nav-is-visible');
		$('.cd-main-header').removeClass('nav-is-visible');
		$('.cd-primary-nav').removeClass('nav-is-visible');
		$('.has-children ul').addClass('is-hidden');
		$('.has-children a').removeClass('selected');
		$('.moves-out').removeClass('moves-out');
		$('.cd-main-content').removeClass('nav-is-visible').one('webkitTransitionEnd otransitionend oTransitionEnd msTransitionEnd transitionend', function(){
			$('body').removeClass('overflow-hidden');
		});
	}

	function toggleSearch(type) {
		if(type=="close") {
			//close serach 
			$('.cd-search').removeClass('is-visible');
			$('.cd-search-trigger').removeClass('search-is-visible');
			$('.cd-overlay').removeClass('search-is-visible');
		} else {
			//toggle search visibility
			$('.cd-search').toggleClass('is-visible');
			$('.cd-search-trigger').toggleClass('search-is-visible');
			$('.cd-overlay').toggleClass('search-is-visible');
			if($(window).width() > MqL && $('.cd-search').hasClass('is-visible')) $('.cd-search').find('input[type="search"]').focus();
			($('.cd-search').hasClass('is-visible')) ? $('.cd-overlay').addClass('is-visible') : $('.cd-overlay').removeClass('is-visible') ;
		}
	}

	function checkWindowWidth() {
		//check window width (scrollbar included)
		var e = window, 
            a = 'inner';
        if (!('innerWidth' in window )) {
            a = 'client';
            e = document.documentElement || document.body;
        }
        if ( e[ a+'Width' ] >= MqL ) {
			return true;
		} else {
			return false;
		}
	}

	function moveSearchBar(){
		var searchBar = $('.cd-search');
		searchBar.detach();
		searchBar.insertBefore('.logo');
	}
	
	$('.jzBoxLink').click(function(event) {
		updatePortrait(event.target);
	});
	
	function updatePortrait(selectedPortrait) {
		var mainPortrait = $('#characterPortrait');
		
		var selectedPortraitSrc = $(selectedPortrait).attr('src');
		mainPortrait.attr('src', selectedPortraitSrc);
		
		$('#imageField').val(selectedPortraitSrc);
	}
	
	
	$("[name='optional']").hide();
	$("#main-div").hide();
	$("#pip-div").hide();
});

function showIFrame(valEl, frame) {
	var val = $(valEl).val();
	
	// Validation:
	var res = val.match(/http.*:\d{4}/g) != null;
	
	if (val != "" && res) {
		$(frame).attr('src', val);
		$(frame + "-div").show();
		$(frame).show();
	} else {
		$(frame).attr('src', '');
		$(frame + "-div").hide();
		$(frame).hide();
	}
}

function toggleElement(el) {
	var elVisible = $(el).is(':visible');
	
	if (elVisible) {
		$(el).hide();
	} else {
		$(el).show();
	}
}

function toggleOptional() {
	toggleElement("[name='optional']");
	if (!$('#pipChk').is(':checked')) {
		$('#pip-div').hide();
	} else {
		showIFrame('#two', '#pip');
	}
}

function switchInputVals(el1, el2) {
	var val1 = $(el1).val();
	var val2 = $(el2).val();
	
	$(el1).val(val2);
	$(el2).val(val1);
}