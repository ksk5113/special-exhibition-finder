<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML>
<!--
	Snapshot by TEMPLATED
	templated.co @templatedco
	Released for free under the Creative Commons Attribution 3.0 license (templated.co/license)
-->
<html>
	<head>
		<title>Special Exhibition Finder</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<link rel="stylesheet" type="text/css" href="/css/main.css" />
	</head>
	<body>
		<div class="page-wrap">

			<!-- Nav -->
				<nav id="nav">
					<ul>
						<li><a href="/index"><span class="icon fa-home"></span></a></li>
						<li><a href="/gallery"><span class="icon fa-camera-retro"></span></a></li>
					</ul>
				</nav>

			<!-- Main -->
				<section id="main">

					<!-- Section -->
						<section>
							<div class="inner">
								<header>
									<h1>${exhibition.name}</h1>
								</header>
								<section class="columns double">
									<div class="column">
										<span class="image left special"><img src="${exhibition.image}" alt="${exhibition.name}" /></span>
										<div class="exhibition_info">
											<h2>전시정보</h2>
											<p>
												<h4>일정</h4> ${exhibition.period}
												<br/><br/>
												<h4>장소</h4> ${exhibition.museum.name} ${exhibition.room}
												<br/><br/>
												<h4>홈페이지</h4> <a href="${exhibition.link}" target="_blank">${exhibition.link}</a>
											</p>
										</div>
									</div>
								</section>
								
								<br/><br/><br/>
								<h2>세부내용</h2>
								<p>${exhibition.description}</p>
								
							</div>
						</section>

					<!-- Footer -->
						<footer id="footer">
							<div class="copyright">
								&copy; Untitled Design: <a href="https://templated.co/">TEMPLATED</a>. Images: <a href="https://unsplash.com/">Unsplash</a>.
							</div>
						</footer>
				</section>
		</div>

		<!-- Scripts -->
			<script src="/js/jquery.min.js"></script>
			<script src="/js/jquery.poptrox.min.js"></script>
			<script src="/js/jquery.scrolly.min.js"></script>
			<script src="/js/skel.min.js"></script>
			<script src="/js/util.js"></script>
			<script src="/js/main.js"></script>

	</body>
</html>